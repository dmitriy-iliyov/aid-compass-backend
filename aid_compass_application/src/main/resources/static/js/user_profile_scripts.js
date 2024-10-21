document.getElementById('connect-wallet').addEventListener('click', function() {
    alert('Connect Wallet clicked!');
    //функционал подключения кошелька здесь
});

document.getElementById('change-account').addEventListener('click', function() {
    alert('Change Account clicked!');
    //функционал изменения аккаунта здесь
});

document.getElementById('logout').addEventListener('click', function() {
    alert('Logout clicked!');
    // const jwt = JSON.parse(document.querySelector("pre#csrf").innerText);
    fetch('/logout?', {
        method: "POST"
    })
});

document.getElementById('delete-account').addEventListener('click', function() {
    if (confirm('Are you sure you want to delete your account?')) {
        alert('Account deleted!');
        //ункционал удаления аккаунта здесь
    }
});
