import { generateReview } from '../services/groqService.js';

export const reviewCode = async (req, res) => {
  try {
    const { code, reviewType = 'JavaScript' } = req.body;

    if (!code) {
      return res.status(400).json({ error: 'Code is required' });
    }

    const result = await generateReview(code, reviewType);
    res.json({ review: result });

  } catch (error) {
    res.status(500).json({ error: 'Failed to review code' });
  }
};