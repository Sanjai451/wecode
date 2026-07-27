import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getProblemById } from '../api/problem'
import Card from '../components/Card'
import EditorPage from './EditorPage'
import { submitCode } from '../api/codeSubmit'

function ProblemDetailPage() {
  const { id } = useParams()
  const [problem, setProblem] = useState(null)
  const [submitLoad, setSubmitLoad] = useState(false);

  const templates = {
    java: `import java.util.*;

public class Main {
    public static void main(String[] args) {

    }
}`,
    python: `def solve():
    pass

if __name__ == "__main__":
    solve()
`
  };

  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState(templates.java);

  const handleLanguageChange = (e) => {
    const lang = e.target.value;
    setLanguage(lang);
    setCode(templates[lang]);
  };

  async function handleCodeSubmit() {

    const codeData = {
      "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "problemId": 1,
      "language": language,
      "code": code
    };
    
    try {
      setSubmitLoad(true)
      console.log("Sending data to backend" + codeData);

      const apiResult = await submitCode(codeData);
      
      console.log('Received data in handler:', apiResult);
      
    } catch (error) {
      console.log('Handler caught the error:', error);
    }
    finally{
      setSubmitLoad(false)
    }
  }

  const handleResetCode = () => {
    setCode(templates[language])
  }

  const handleRunTests = () => {
    console.log("Running Test cases")
  }

  useEffect(() => {
    let isMounted = true

    getProblemById(id).then((data) => {
      if (isMounted) {
        setProblem(data)
      }
    })

    return () => {
      isMounted = false
    }
  }, [id])

  if (!problem) {
    return <p>Loading problem...</p>
  }

  return (
    <div className="problem-page">
      <div className="page-stack problem-details-panel">
        <div className="page-header problem-title-row">
          <div>
            <p className="eyebrow">Challenge</p>
            <h2>{problem.title}</h2>
          </div>
          <span className="difficulty-pill">{problem.difficulty}</span>
        </div>
            <Card title="Problem statement" description={problem.description + 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliquLorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
            }>
                <div className="problem-meta">
                <span>{problem.category}</span>
                {
                    problem.tags.map(e => <span className="tag">{e}</span>)
                }
                </div>
            </Card>
            {
              language == 'java' && 
              <span>Note : Your Main class name needs to be Solution</span>
            }
        </div>
        <div className="code-editor-panel">
          <div className="editor-toolbar">
            <h3>Solution workspace</h3>
            <div className="action-buttons">
              <button type="button" disabled={submitLoad} onClick={handleResetCode}>Reset Code</button>
              <button type="button" disabled={submitLoad} onClick={handleRunTests}>Run Tests</button>
              <button type="button" disabled={submitLoad} onClick={handleCodeSubmit}>Submit Solution</button>
            </div>
          </div>
          <EditorPage handleLanguageChange={handleLanguageChange} 
                      code={code}  
                      language={language}
                      setCode={setCode}
                      setLanguage={setLanguage}
          />
        </div>

    </div>
  )
}

export default ProblemDetailPage
