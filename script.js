// ---- Version 2: Matching Algorithm ----

// Common words we ignore because they don't carry real meaning
const STOPWORDS = new Set([
  'a','an','the','and','or','but','is','are','was','were','be','been',
  'in','on','at','to','for','of','with','by','as','this','that','it',
  'from','will','can','has','have','had','i','you','we','they','your',
  'our','their','my','his','her','its'
]);

// Turns a block of text into a clean Set of meaningful words
function extractKeywords(text) {
  return new Set(
    text
      .toLowerCase()                     // normalize case
      .replace(/[^a-z0-9\s]/g, ' ')       // remove punctuation
      .split(/\s+/)                      // split into words
      .filter(word => word.length > 2 && !STOPWORDS.has(word)) // remove junk
  );
}

// Compares resume keywords vs job description keywords
function calculateMatch(resumeText, jdText) {
  const resumeWords = extractKeywords(resumeText);
  const jdWords = extractKeywords(jdText);

  const jdWordsArray = Array.from(jdWords);
  const matchedWords = jdWordsArray.filter(word => resumeWords.has(word));
  const missingWords = jdWordsArray.filter(word => !resumeWords.has(word));

  const score = jdWordsArray.length === 0
    ? 0
    : Math.round((matchedWords.length / jdWordsArray.length) * 100);

  return { score, missingWords };
}

// ---- Hook everything up to the button ----
document.getElementById('checkBtn').addEventListener('click', () => {
  const resumeText = document.getElementById('resumeInput').value.trim();
  const jdText = document.getElementById('jdInput').value.trim();

  if (!resumeText || !jdText) {
    alert('Please fill in both fields before checking.');
    return;
  }

  const { score, missingWords } = calculateMatch(resumeText, jdText);

  // Show results section
  const resultsSection = document.getElementById('results');
  resultsSection.classList.remove('hidden');

  // Update score text and bar
  document.getElementById('scoreText').textContent = `Score: ${score}%`;
  document.getElementById('scoreFill').style.width = `${score}%`;

  // Show missing keywords
  const list = document.getElementById('missingKeywords');
  list.innerHTML = '';

  if (missingWords.length === 0) {
    list.innerHTML = '<li>Great! No major keywords missing.</li>';
  } else {
    missingWords.slice(0, 15).forEach(word => {
      const li = document.createElement('li');
      li.textContent = word;
      list.appendChild(li);
    });
  }
});
