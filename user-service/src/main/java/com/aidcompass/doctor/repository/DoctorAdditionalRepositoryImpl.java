package com.aidcompass.doctor.repository;

import com.aidcompass.doctor.models.DoctorEntity;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DoctorAdditionalRepositoryImpl implements DoctorAdditionalRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Slice<DoctorEntity> findAllWithEntityGraphBySpecification(Specification<DoctorEntity> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DoctorEntity> query = cb.createQuery(DoctorEntity.class);
        Root<DoctorEntity> root = query.from(DoctorEntity.class);
        query.select(root).where(spec.toPredicate(root, query, cb));

        TypedQuery<DoctorEntity> typedQuery = entityManager.createQuery(query);

        EntityGraph<?> graph = entityManager.getEntityGraph("doctor.specializations");
        typedQuery.setHint("jakarta.persistence.fetchgraph", graph);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<DoctorEntity> resultList = typedQuery.getResultList();
        return new SliceImpl<>(resultList, pageable, resultList.size() == pageable.getPageSize());
    }
}