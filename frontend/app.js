console.log("app.js loaded")
// One place that knows where the backend lives,
// when app is deployed this line changes, nothing else does
const API = "http://localhost:8080";


const queueEl = document.querySelector("#queue");
const refreshBtn = document.querySelector("#refresh-btn");

async function loadQueue() {
    const response = await fetch(`${API}/api/logs/all`);
    const problems = await response.json(); // an ARRAY this time

    console.log(problems);

    queueEl.textContent = "";

    for(const p of problems) {
        const li = document.createElement("li");
        li.textContent = `#${p.problemNumber} ${p.title} - ${p.pattern} - next recall ${p.nextRecall}`;
        queueEl.append(li);
    }
}
refreshBtn.addEventListener("click", loadQueue);
// load problems on arrival
loadQueue();
const probeBtn = document.querySelector("#probe-btn");

probeBtn.addEventListener("click", async function () {
    const id = 1;
    const response = await fetch(`${API}/api/logs/validate?id=${id}`);

    console.log("status", response.status);

    const problem = await response.json();
    console.log(problem);
})

// Get references once
const testBtn = document.querySelector("#test-btn");
const statusEl = document.querySelector("#status");

// addEventListeners takes two arguments
// 1. the even name, as a string, "click"
// 2. a function to run when that happens
testBtn.addEventListener("click", function() {
    statusEl.textContext = "Javascript is running.";
    console.log("button was clicked");
});

// Add Problem
const addForm = document.querySelector("#add-form");
const problemIdInput = document.querySelector("#problem-id");
const confidenceInput = document.querySelector("#confidence");
const patternInput = document.querySelector("#pattern");
const failLogInput = document.querySelector("#fail-log");

addForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    // Read the inputs and  convert at the edge
    // These four keys are RecordRequest's four fields exactly
    const body = {
        problemID: Number(problemIdInput.value),
        confidence: Number(confidenceInput.value),
        pattern: patternInput.value,
        failLog: failLogInput.value
    };

    console.log("sending:", JSON.stringify(body));

    const response = await fetch(`${API}/api/logs/record`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    const text = await response.text();
    console.log("server said:", text);

    await loadQueue();
});

