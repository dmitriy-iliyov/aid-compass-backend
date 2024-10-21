document.querySelector("button#get-csrf").onclick = _ => {
    fetch("/csrf").then(response => {
        if (response.status !== 200) {
            throw new Error(`Unexpected status: ${response.status}`)
        }
        return response.text();
    })
}
