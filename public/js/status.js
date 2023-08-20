var submissions = []

var ip = "http://54.211.158.145:2358";


async function ready() {
    if (document.cookie.includes("userid=")) {
        var userid = document.cookie.substring(document.cookie.indexOf("=") + 1);

        const getinfo = await fetch("https://parseapi.back4app.com/classes/contestant/", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-Parse-Application-Id": "ydpBQ9tdLUa7OkXsfHdXxhvLio5gltRSsKYYFqeX",
                "X-Parse-REST-API-Key": "z0bblcIvW9Ltit6bJbHxegUGnMYV1w29CziDD4SI"
            },
        });

        var response = await getinfo.json();
        for (var i = 0; i < response.results.length; i++) {
            if (response.results[i].objectId !== userid) continue;
            var username = response.results[i].name;
            var nameh3 = document.getElementById("nameshow");
            nameh3.innerHTML = username;
            nameh3.style.display = "block";
        }

        const getSubmissions = await fetch("https://parseapi.back4app.com/classes/submission", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "X-Parse-Application-Id": "ydpBQ9tdLUa7OkXsfHdXxhvLio5gltRSsKYYFqeX",
                "X-Parse-REST-API-Key": "z0bblcIvW9Ltit6bJbHxegUGnMYV1w29CziDD4SI"
            }
        });

        var allSubmissions = await getSubmissions.json();


        for (var i = 0; i < allSubmissions.results.length; i++) {
            if (allSubmissions.results[i].User !== userid) continue;
            submissions.push(allSubmissions.results[i]);

            var verdict = allSubmissions.results[i].verdict;
            for (var token of allSubmissions.results[i].tokens) {

                if (allSubmissions.results[i].process) {

                    var link = ip + "/submissions/" + token + "/?base64_encoded=false";
                    const checkStatus = await fetch(link, {
                        method: "GET",
                        headers: { "Content-Type": "application/json" }
                    });
                    
                    var newResponse = await checkStatus.json();
                    verdict = newResponse.status.description;
                    var process = verdict === "In Queue" || verdict === "Processing";
                    await fetch("https://parseapi.back4app.com/classes/submission/" + allSubmissions.results[i].objectId, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json",
                            "X-Parse-Application-Id": "ydpBQ9tdLUa7OkXsfHdXxhvLio5gltRSsKYYFqeX",
                            "X-Parse-REST-API-Key": "z0bblcIvW9Ltit6bJbHxegUGnMYV1w29CziDD4SI"
                        },
                        body: JSON.stringify({
                            process: process,
                            verdict: verdict
                        })
                    });
                    
                }
            }
            console.log(verdict);
        }

        console.log(submissions)
        updateSubmissions();
    }
}

function updateSubmissions() {
    var table = document.getElementById("submissionTable");
    // <tr class="row1">
    //                                 <td align="center">Pantun Grader (f)</td>
    //                                 <td align="center">4:23</td>
    //                                 <td style="text-align: center;"><img height="15" width="15"
    //                                       src="css/Status_files/sparta-x.svg">&nbsp;Wrong Answer</td>
    //                              </tr>
    //                              <tr class="row0">
    //                                 <td align="center">The Mountain of Gold? (a)</td>
    //                                 <td align="center">3:21</td>
    //                                 <td style="text-align: center;"><img height="15" width="15"
    //                                       src="css/Status_files/sparta-check.svg">&nbsp;Correct!</td>
    //                              </tr>

    for(var i = 0; i < submissions.length; i++){
        const row = document.createElement("tr");
        if(i%2===0)
            row.className = "row0";
        else
            row.className = "row1";
        const name = document.createElement("td");
        name.className = "center";
        name.innerHTML = submissions[0].problem;

        const time = document.createElement("td");
        time.className = "center";
        time.innerHTML = submissions[0].time;

        const verdict = document.createElement("td");
        verdict.style = "text-align: center;"

        const image = document.createElement("img");
        image.height = 15;
        image.width = 15;
        image.src = submissions[i].verdict.indexOf("Accepted") !== -1 ? "css/Status_files/sparta-check.svg" : "css/Status_files/sparta-x.svg";
        verdict.innerHTML = "&nbsp;" + submissions[i].verdict;
        verdict.appendChild(image);


        row.appendChild(name);
        row.appendChild(time);
        row.appendChild(verdict);
        table.appendChild(row);
    }

}


ready();


async function createUser() {
    var username = document.getElementById("fname").value;
    const create = await fetch("https://parseapi.back4app.com/classes/contestant", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-Parse-Application-Id": "ydpBQ9tdLUa7OkXsfHdXxhvLio5gltRSsKYYFqeX",
            "X-Parse-REST-API-Key": "z0bblcIvW9Ltit6bJbHxegUGnMYV1w29CziDD4SI"
        },
        body: JSON.stringify({
            name: username,
            submissions: []
        })
    });

    var userId = await create.json();
    document.cookie = "userid=" + userId.objectId;
    var nameh3 = document.getElementById("nameshow");
    nameh3.innerHTML = username;
    nameh3.style.display = "block";
    console.log(userId);
}