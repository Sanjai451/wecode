import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getProblemById } from '../api/problem'
import Card from '../components/Card'
import EditorPage from './EditorPage'

function ProblemDetailPage() {
  const { id } = useParams()
  const [problem, setProblem] = useState(null)

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
        </div>
        <div className="code-editor-panel">
          <div className="editor-toolbar">
            <h3>Solution workspace</h3>
            <div className="action-buttons">
              <button type="button">Reset Code</button>
              <button type="button">Run Tests</button>
              <button type="button">Submit Solution</button>
            </div>
          </div>
          <EditorPage />
        </div>

    </div>
  )
}

export default ProblemDetailPage
