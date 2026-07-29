import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getProblemById } from '../api/problem'
import Card from '../components/Card'
import EditorPage from './EditorPage'
import { submitCode, submitTestCase } from '../api/codeSubmit'
import ProblemsDescription from '../components/ProblemsDescription'
import ProblemsSubmission from '../components/ProblemsSubmission'
import SubmissionResults from '../components/SubmissionResults'

function ProblemDetailPage() {
  const { id } = useParams()
  const [problem, setProblem] = useState(null)
  const [submitLoad, setSubmitLoad] = useState(false);
  const [view, setView] = useState("description"); // "description" or "submissions"
  const [submissions, setSubmissions] = useState([]);
  const [submissionResults, setSubmissionResults] = useState(null);

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

  const submissionData = {
  "submissionId": "9d8d66f3-c5a4-4d89-8e12-7e67f3a8d2c1",
  "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "problemId": 1,
  "language": "java",
  "verdict": "Accepted",
  "runtimeMs": 18,
  "memoryMb": 42,
  "submittedAt": "2026-07-29T10:42:31",
  "totalCases": 15,
  "failedCases": 0,
  "message": "Congratulations! Your solution passed all 15 test cases."
}

  const setSubmissionResultsHandler = (res) => {
    setSubmissionResults(res);
  }

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
      setSubmissionResultsHandler(submissionData);
      setSubmitLoad(false)
    }
  }

  const handleResetCode = () => {
    setCode(templates[language])
  }

  const handleRunTests = async () => {
    const codeData = {
      "userId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "problemId": 1,
      "language": language,
      "code": code
    };
    
    try {
      setSubmitLoad(true)
      console.log("Sending data to backend" + codeData);

      const apiResult = await submitTestCase(codeData);
      
      console.log('Received data in handler:', apiResult);
      
    } catch (error) {
      console.log('Handler caught the error:', error);
    }
    finally{
      setSubmitLoad(false);
      setSubmissionResultsHandler(submissionData);
    }
  }

  const handleViewChange = (newView) => {
    setView(newView);
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
            <h2>{problem.title}</h2>
          </div>
          <span className="difficulty-pill">{problem.difficulty}</span>
        </div>

            <div className="option-tab">
              <button onClick={() => handleViewChange("description")}>Description</button>
              <button onClick={() => handleViewChange("submissions")}>Submissions</button>
            </div>
            {
              view === "description" ? (
                  <ProblemsDescription problem={problem} />
              ) : (
                <div>
                  <ProblemsSubmission setSubmissions={setSubmissions} submissions={submissions} />
                </div>
              )
            }

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
                      submissionResults={submissionResults}
          />
        </div>
        
    </div>
  )
}

export default ProblemDetailPage
