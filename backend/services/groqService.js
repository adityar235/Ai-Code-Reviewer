import fetch from 'node-fetch';

export const generateReview = async (code) => {
  try {
    const apiKey = process.env.GROQ_API_KEY;
    
    if (!apiKey || apiKey === 'your_groq_api_key_here') {
      return getMockReview(code);
    }
    
    const response = await fetch('https://api.groq.com/openai/v1/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`,
      },
      body: JSON.stringify({
        model: 'llama-3.3-70b-versatile',
        messages: [
          {
            role: 'user',
            content: `Review this code and find the programming language automatically. Provide detailed review with bugs, performance, security, and improvements:\n\n${code}`
          }
        ],
        max_tokens: 1000
      })
    });

    const data = await response.json();
    return data.choices[0].message.content;
    
  } catch (error) {
    return getMockReview(code);
  }
};

function getMockReview(code) {
  return `Code Review:

1. Bugs: Check for edge cases
2. Performance: Optimize loops if needed
3. Security: Validate inputs
4. Improvements: Add error handling

Note: Configure GROQ_API_KEY in .env for real AI review.`;
}