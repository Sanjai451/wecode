import Card from '../../components/ui/Card'

const submissions = [
  { id: 1, problem: 'Two Sum', status: 'Accepted', score: 100 },
  { id: 2, problem: 'Valid Parentheses', status: 'Wrong Answer', score: 60 },
]

function SubmissionPage() {
  return (
    <div className="page-stack">
      <div className="page-header">
        <div>
          <p className="eyebrow">History</p>
          <h2>Recent submissions</h2>
        </div>
      </div>

      <div className="grid-list">
        {submissions.map((submission) => (
          <Card key={submission.id} title={submission.problem} description={`Score: ${submission.score}`}>
            <p className="submission-status">{submission.status}</p>
          </Card>
        ))}
      </div>
    </div>
  )
}

export default SubmissionPage
