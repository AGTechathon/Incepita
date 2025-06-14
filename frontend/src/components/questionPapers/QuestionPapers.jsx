import React, { useEffect, useState } from 'react';
import './QuestionPapers.css';
import Sidebar from '../sidebar/Sidebar';

function QuestionPapers() {
  const [questionPapers, setQuestionPapers] = useState([]);
  const [error, setError] = useState(null);

  const getAllQuestionPapers = async (username) => {
    try {
      const response = await fetch(`http://localhost:8080/api/question-paper/all/${username}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch papers: ${response.status}`);
      }

      const data = await response.json();
      console.log(data)
      setQuestionPapers(data);
    } catch (err) {
      console.error(err);
      setError(err.message);
    }
  };

  useEffect(() => {
    const username = localStorage.getItem("username");
    if (username) {
      getAllQuestionPapers(username);
    } else {
      setError("User not logged in.");
    }
  }, []);

  return (
    <div className="questionpapers-wrapper">
      <Sidebar />


      <div className="questionpapers-content">
        <h2>Your Generated Question Papers</h2>
        {error && <p style={{ color: 'red' }}>{error}</p>}

        <div className="papers-grid">
          {questionPapers.length === 0 && !error && <p>No question papers found.</p>}
          {questionPapers.map((paper, index) => {
            const username = localStorage.getItem("username");
            const viewUrl = `http://localhost:5173/${username}/papers/${paper.questionPaperId}/${paper.versionNo || 1}`;
            return (
              <div className="paper-card" key={index}>
                <h3>{paper.paperName || 'Untitled'}</h3>
                <p><strong>Created:</strong> {new Date(paper.createAt).toLocaleDateString()}</p>
                <p><strong>Last Modified:</strong> {new Date(paper.lastModified).toLocaleDateString()}</p>
                <a
                  href={viewUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  View Paper
                </a>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default QuestionPapers;
