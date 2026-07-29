import React from 'react'
import Card from './Card'

const ProblemsDescription = ({problem}) => {
  return (
    <>
      <Card title="Problem Statement">
        <div className="problem-content">
          <pre>{problem.description}</pre>
        </div>
      </Card>

      {problem.inputFormat && (
        <Card title="Input Format">
          <pre>{problem.inputFormat}</pre>
        </Card>
      )}

      {problem.outputFormat && (
        <Card title="Output Format">
          <pre>{problem.outputFormat}</pre>
        </Card>
      )}

      {problem.constraints && (
        <Card title="Constraints">
          <pre>{problem.constraints}</pre>
        </Card>
      )}

      {problem.explanation && (
        <Card title="Explanation">
          <pre>{problem.explanation}</pre>
        </Card>
      )}

      { problem.tags.map(e => 
            <span className="tag">{e}</span>) } 

      <Card title="Limits">
        <div className="problem-limits">
          <div>
            <strong>Time Limit:</strong> {problem.timeLimit} sec
          </div>

          <div>
            <strong>Memory Limit:</strong> {problem.memoryLimit} MB
          </div>
        </div>
      </Card>
    </>
  );
}

export default ProblemsDescription
