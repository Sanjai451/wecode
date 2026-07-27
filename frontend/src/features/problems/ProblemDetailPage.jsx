import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { getProblemById } from '../../api/problem'
import Card from '../../components/ui/Card'

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
    <div className="page-stack">
      <div className="page-header">
        <div>
          <p className="eyebrow">Challenge</p>
          <h2>{problem.title}</h2>
        </div>
        <Link className="primary-button" to="/editor">
          Solve in editor
        </Link>
      </div>

      <Card title="Problem statement" description={problem.description}>
        <div className="problem-meta">
          <span>{problem.category}</span>
          <span>{problem.difficulty}</span>
          <span>{problem.tags.join(' • ')}</span>
        </div>
      </Card>
    </div>
  )
}

export default ProblemDetailPage
