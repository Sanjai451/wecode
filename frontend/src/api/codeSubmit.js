import axios from 'axios';

const URL = 'http://localhost:8085/submissions/submit'

export async function submitCode(data) {
  try {
    const response = await axios.post(URL, data);
    
    return response.data; 
  } catch (error) {
    console.error('API Error:', error.message);
    
    throw error; 
  }
}