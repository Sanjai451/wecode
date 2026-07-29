import React from "react";

const SubmissionResults = ({ submission }) => {
  if (!submission) return null;

  const passedCases = submission.totalCases - submission.failedCases;

  const verdictClass = submission.verdict
    .toLowerCase()
    .replace(/\s+/g, "-");

  return (
    <div className={`submission-result ${verdictClass}`}>
      <div className="submission-header">
        <h2>{submission.verdict}</h2>
        <span className="submission-language">
          {submission.language.toUpperCase()}
        </span>
      </div>

      <div className="submission-grid">
        <div className="submission-item">
          <span className="label">Runtime</span>
          <span>{submission.runtimeMs} ms</span>
        </div>

        <div className="submission-item">
          <span className="label">Memory</span>
          <span>{submission.memoryMb} MB</span>
        </div>

        <div className="submission-item">
          <span className="label">Test Cases</span>
          <span>
            {passedCases} / {submission.totalCases}
          </span>
        </div>

        <div className="submission-item">
          <span className="label">Submitted</span>
          <span>
            {new Date(submission.submittedAt).toLocaleString()}
          </span>
        </div>
      </div>

      {submission.message && (
        <div className="submission-message">
          <h4>Message</h4>
          <p>{submission.message}</p>
        </div>
      )}
    </div>
  );
};

export default SubmissionResults;