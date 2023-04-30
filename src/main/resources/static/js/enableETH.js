let userId = 0

window.addEventListener('load', async function () {
    if (window.ethereum) {
        console.log('Web3 Detected! ' + window.ethereum.constructor.name)
        window.web3 = new Web3(window.ethereum);
        try {
            await window.ethereum.enable()
        } catch (error) {
            console.error(error)
        }

    } else {
        console.log('No Web3 Detected... using HTTP Provider')
        window.web3 = new Web3(new Web3.providers.HttpProvider("https://sepolia.infura.io/v3/304127ae5cee4e06b114cb3d57749335"))
    }

    auth = localStorage.getItem('auth')
    userId = parseInt(localStorage.getItem('userId'), 10)
    console.log(auth)
    console.log(userId)
    if(window.location.href !== "http://localhost:8080/auth")
        if(/^false$/i.test(auth) && userId === 0)
            window.location.replace("http://localhost:8080/auth");
    localStorage.setItem('auth', false)
    localStorage.setItem('userId', 0)
})

window.addEventListener('unload', async function() {
    if(window.location.href !== "http://localhost:8080/auth")
        localStorage.setItem('userId', userId)
});
