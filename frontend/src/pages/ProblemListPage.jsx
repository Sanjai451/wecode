import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getProblems } from '../api/problem'
import Card from '../components/Card'

function ProblemListPage() {
  const [problems, setProblems] = useState([])

  useEffect(() => {
    let isMounted = true

    getProblems().then((data) => {
      if (isMounted) {
        setProblems(data)
      }
    })

    return () => {
      isMounted = false
    }
  }, [])

  return (
    <div className="page-stack">
      <div className="page-header">
        <div>
          <p className="eyebrow">Practice</p>
          <h2>Explore coding problems</h2>
        </div>
        <Link className="primary-button" to="/editor">
          Open editor
        </Link>
      </div>

      <div className="grid-list">
        {problems.map((problem) => (
          <Card
            key={problem.id}
            title={problem.title}
            description={problem.description}
            action={<span className="tag">{problem.difficulty}</span>}
          >
            <div className="problem-meta">
              <span>{problem.category}</span>
              <span>{problem.tags.join(' • ')}</span>
            </div>
            <Link className="secondary-button" to={`/problems/${problem.id}`}>
              View details
            </Link>
          </Card>
        ))}
      </div>
    </div>
  )
}

export default ProblemListPage
