// ---- Version 3 (Polished): calls Java backend, with better UX ----

const resumeInput = document.getElementById('resumeInput');
const jdInput = document.getElementById('jdInput');
const resumeCount = document.getElementById('resumeCount');
const jdCount = document.getElementById('jdCount');
const checkBtn = document.getElementById('checkBtn');
const btnText = document.getElementById('btnText');
const btnSpinner = document.getElementById('btnSpinner');
const clearBtn = document.getElementById('clearBtn');
const errorMsg = document.getElementById('errorMsg');
const resultsSection = document.getElementById('results');

const SCORE_CIRCUMFERENCE = 327; // matches the SVG circle's stroke-dasharray

// Live word count as user types
function countWords(text) {
  return text.trim() === '' ? 0 : text.trim().split(/\s+/).length;
}

resumeInput.addEventListener('input', () => {
  resumeCount.textContent = `${countWords(resumeInput.value)} words`;
});

jdInput.addEventListener('input', () => {
  jdCount.textContent = `${countWords(jdInput.value)} words`;
});

// Clear button resets everything
clearBtn.addEventListener('click', () => {
  resumeInput.value = '';
  jdInput.value = '';
  resumeCount.textContent = '0 words';
  jdCount.textContent = '0 words';
  resultsSection.classList.add('hidden');
  errorMsg.classList.add('hidden');
});

function setLoading(isLoading) {
  checkBtn.disabled = isLoading;
  btnText.textContent = isLoading ? 'Checking...' : 'Check Match Score';
  btnSpinner.classList.toggle('hidden', !isLoading);
}

function showError(message) {
  errorMsg.textContent = message;
  errorMsg.classList.remove('hidden');
}

function getScoreLabel(score) {
  if (score >= 80) return 'Excellent Match!';
  if (score >= 60) return 'Good Match';
  if (score >= 40) return 'Needs Improvement';
  return 'Low Match';
}

checkBtn.addEventListener('click', async () => {
  const resumeText = resumeInput.value.trim();
  const jdText = jdInput.value.trim();

  errorMsg.classList.add('hidden');

  if (!resumeText || !jdText) {
    showError('Please fill in both fields before checking.');
    return;
  }

  setLoading(true);

  try {
    const response = await fetch('http://localhost:8080/api/match', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ resumeText, jdText })
    });

    if (!response.ok) {
      throw new Error('Server responded with an error');
    }

    const data = await response.json(); // { score: ..., missingKeywords: [...] }

    // Reveal results
    resultsSection.classList.remove('hidden');

    // Animate circular score
    const offset = SCORE_CIRCUMFERENCE - (SCORE_CIRCUMFERENCE * data.score) / 100;
    document.getElementById('scoreCircleFg').style.strokeDashoffset = offset;
    document.getElementById('scoreNumber').textContent = `${data.score}%`;
    document.getElementById('scoreLabel').textContent = getScoreLabel(data.score);

    // Show missing keywords
    const list = document.getElementById('missingKeywords');
    list.innerHTML = '';

    if (data.missingKeywords.length === 0) {
      list.innerHTML = '<li>🎉 No major keywords missing!</li>';
    } else {
      data.missingKeywords.slice(0, 15).forEach(word => {
        const li = document.createElement('li');
        li.textContent = word;
        list.appendChild(li);
      });
    }

    resultsSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

  } catch (err) {
    showError('Could not reach the server. Make sure the Java backend is running on port 8080.');
    console.error(err);
  } finally {
    setLoading(false);
  }
});
// ---- Version 3 (Polished): calls Java backend, with better UX ----

const resumeInput = document.getElementById('resumeInput');
const jdInput = document.getElementById('jdInput');
const resumeCount = document.getElementById('resumeCount');
const jdCount = document.getElementById('jdCount');
const checkBtn = document.getElementById('checkBtn');
const btnText = document.getElementById('btnText');
const btnSpinner = document.getElementById('btnSpinner');
const clearBtn = document.getElementById('clearBtn');
const errorMsg = document.getElementById('errorMsg');
const resultsSection = document.getElementById('results');

const SCORE_CIRCUMFERENCE = 327; // matches the SVG circle's stroke-dasharray

// Live word count as user types
function countWords(text) {
  return text.trim() === '' ? 0 : text.trim().split(/\s+/).length;
}

resumeInput.addEventListener('input', () => {
  resumeCount.textContent = `${countWords(resumeInput.value)} words`;
});

jdInput.addEventListener('input', () => {
  jdCount.textContent = `${countWords(jdInput.value)} words`;
});

// Clear button resets everything
clearBtn.addEventListener('click', () => {
  resumeInput.value = '';
  jdInput.value = '';
  resumeCount.textContent = '0 words';
  jdCount.textContent = '0 words';
  resultsSection.classList.add('hidden');
  errorMsg.classList.add('hidden');
});

function setLoading(isLoading) {
  checkBtn.disabled = isLoading;
  btnText.textContent = isLoading ? 'Checking...' : 'Check Match Score';
  btnSpinner.classList.toggle('hidden', !isLoading);
}

function showError(message) {
  errorMsg.textContent = message;
  errorMsg.classList.remove('hidden');
}

function getScoreLabel(score) {
  if (score >= 80) return 'Excellent Match!';
  if (score >= 60) return 'Good Match';
  if (score >= 40) return 'Needs Improvement';
  return 'Low Match';
}

checkBtn.addEventListener('click', async () => {
  const resumeText = resumeInput.value.trim();
  const jdText = jdInput.value.trim();

  errorMsg.classList.add('hidden');

  if (!resumeText || !jdText) {
    showError('Please fill in both fields before checking.');
    return;
  }

  setLoading(true);

  try {
    const response = await fetch('http://localhost:8080/api/match', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ resumeText, jdText })
    });

    if (!response.ok) {
      throw new Error('Server responded with an error');
    }

    const data = await response.json(); // { score: ..., missingKeywords: [...] }

    // Reveal results
    resultsSection.classList.remove('hidden');

    // Animate circular score
    const offset = SCORE_CIRCUMFERENCE - (SCORE_CIRCUMFERENCE * data.score) / 100;
    document.getElementById('scoreCircleFg').style.strokeDashoffset = offset;
    document.getElementById('scoreNumber').textContent = `${data.score}%`;
    document.getElementById('scoreLabel').textContent = getScoreLabel(data.score);

    // Show missing keywords
    const list = document.getElementById('missingKeywords');
    list.innerHTML = '';

    if (data.missingKeywords.length === 0) {
      list.innerHTML = '<li>🎉 No major keywords missing!</li>';
    } else {
      data.missingKeywords.slice(0, 15).forEach(word => {
        const li = document.createElement('li');
        li.textContent = word;
        list.appendChild(li);
      });
    }

    // Show personalized suggestions
    const suggestionsList = document.getElementById('suggestionsList');
    suggestionsList.innerHTML = '';
    (data.suggestions || []).forEach(suggestion => {
      const li = document.createElement('li');
      li.textContent = suggestion;
      suggestionsList.appendChild(li);
    });

    resultsSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

  } catch (err) {
    showError('Could not reach the server. Make sure the Java backend is running on port 8080.');
    console.error(err);
  } finally {
    setLoading(false);
  }
});
