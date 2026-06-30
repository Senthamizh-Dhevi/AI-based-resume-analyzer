// ---- Version 3: Now calls the Java backend instead of computing locally ----

document.getElementById('checkBtn').addEventListener('click', async () => {
  const resumeText = document.getElementById('resumeInput').value.trim();
  const jdText = document.getElementById('jdInput').value.trim();

  if (!resumeText || !jdText) {
    alert('Please fill in both fields before checking.');
    return;
  }

  try {
    // Send data to our Java backend running on localhost:8080
    const response = await fetch('http://localhost:8080/api/match', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ resumeText, jdText })
    });

    const data = await response.json(); // { score: ..., missingKeywords: [...] }

    // Show results section
    const resultsSection = document.getElementById('results');
    resultsSection.classList.remove('hidden');

    document.getElementById('scoreText').textContent = `Score: ${data.score}%`;
    document.getElementById('scoreFill').style.width = `${data.score}%`;

    const list = document.getElementById('missingKeywords');
    list.innerHTML = '';

    if (data.missingKeywords.length === 0) {
      list.innerHTML = '<li>Great! No major keywords missing.</li>';
    } else {
      data.missingKeywords.slice(0, 15).forEach(word => {
        const li = document.createElement('li');
        li.textContent = word;
        list.appendChild(li);
      });
    }
  } catch (err) {
    alert('Could not reach the server. Make sure the Java backend is running on port 8080.');
    console.error(err);
  }
});
