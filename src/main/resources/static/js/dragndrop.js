const dropZone = document.getElementById("drop-zone");
const fileInput = document.getElementById("file-input");

dropZone.addEventListener("click", function (event) {
    if (event.target !== fileInput) {
        fileInput.click();
    }
});

dropZone.addEventListener("dragover", function (event) {
    event.preventDefault();
    dropZone.classList.add("dragover");
});

dropZone.addEventListener("dragleave", function () {
    dropZone.classList.remove("dragover");
});

dropZone.addEventListener("drop", function (event) {
    event.preventDefault();
    dropZone.classList.remove("dragover");
    fileInput.files = event.dataTransfer.files;
    const fileName = fileInput.files[0].name;
    const fileSize = fileInput.files[0].size;
    document.querySelector(".file-upload-text").textContent = fileName;
    console.log("Выбран файл:", fileName, "(", fileSize, " байт )");
});

fileInput.addEventListener("change", function () {
    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        const fileSize = fileInput.files[0].size;
        document.querySelector(".file-upload-text").textContent = fileName;
        console.log("Выбран файл:", fileName, "(", fileSize, " байт )");
    }
});