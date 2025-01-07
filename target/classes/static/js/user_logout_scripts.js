function findCookie() {
    const name = "X-X"
    const cookie = document.cookie.split(";")
}


document.querySelector("button#logout-button").onclick = _ => {
    const jwt = JSON.parse(document.querySelector("pre#csrf").innerText);
    fetch(`/logout?${jwt.parameterName}=${jwt.token}`, {
        method: "POST"
    }).then(_ => {
        document.querySelector("pre#csrf").innerText = '';
        document.querySelector("pre#greeting").innerText = '';
    })
}