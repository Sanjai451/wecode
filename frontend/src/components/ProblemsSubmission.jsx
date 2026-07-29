import React, { useEffect } from 'react'
import { getSubmissionsForProblem } from '../api/submissionForProblems';

const ProblemsSubmission = ({ setSubmissions, submissions }) => {

    useEffect(() => {
    async function fetchSubmissions() {
        if (!submissions || submissions.length === 0) {
            const data = await getSubmissionsForProblem(1);
            setSubmissions(data);
            console.log(data);
        }
    }

    fetchSubmissions();
}, [setSubmissions, submissions]);

    if (!submissions) {
        return <p>Loading submissions...</p>;
    }

    if (submissions.length === 0) {
        return (
            <p>No submissions yet.</p>
        );
    }

    return (
        <table className="submission-table">
            <thead>
                <tr>
                    <th>Verdict</th>
                    <th>Language</th>
                    <th>Cases</th>
                    <th>Submitted</th>
                </tr>
            </thead>

            <tbody>
                {submissions &&  submissions.map((submission) => (
                    <tr key={submission.submissionId}>
                        <td>{submission.verdict}</td>
                        <td>{submission.language}</td>
                        <td>
                            {submission.totalCases - submission.failedCases}
                            /
                            {submission.totalCases}
                        </td>
                        <td>
                            {new Date(submission.submittedAt).toLocaleString()}
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

export default ProblemsSubmission
