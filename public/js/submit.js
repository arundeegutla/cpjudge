const languageID = new Map();
languageID.set('cpp', 54);
languageID.set('java', 62);

async function submitProgram() {
    const fileInput = document.getElementById("file");
    
    for (const file of fileInput.files) {
        let content = await convert2DataUrl(file);
        var lang = languageID.get(file.name.substring(file.name.indexOf(".") + 1));
        var problemName = file.name.substring(0, file.name.indexOf("."));
        content = content.replaceAll(problemName, "Main");
        var thisTime = new Date();
        var userId = document.cookie.substring(document.cookie.indexOf("=") + 1);
        
        var json = {
            problemName: problemName,
            languageID: lang,
            userId: userId,
            program: content,
            time: thisTime.getTime()
        };
        
        await fetch('/submitProgram', {
            method: 'POST',
            body: JSON.stringify(json),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        window.location.href = "/status";

        console.log(window.location.href);
    }
}

async function convert2DataUrl(blobOrFile) {
    let reader = new FileReader()
    reader.readAsBinaryString(blobOrFile)
    await new Promise(resolve => reader.onloadend = () => resolve())
    return reader.result
}