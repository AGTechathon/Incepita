import React from 'react';
import './QuestionPapers.css';
import Sidebar from '../sidebar/Sidebar';

const questionPapers = [
  {
    title: 'Unit Test 1',
    date: 'June 10, 2025',
    subject: 'Mathematics',
    file: 'unit1.pdf',
  },
  {
    title: 'Midterm Exam',
    date: 'June 5, 2025',
    subject: 'Data Structures',
    file: 'midterm.pdf',
  },
  {
    title: 'Final Exam',
    date: 'May 30, 2025',
    subject: 'Operating Systems',
    file: 'final.pdf',
  },
];

function QuestionPapers() {
  return (
    <div className="questionpapers-wrapper">
      <Sidebar />

      <div className="questionpapers-content">
        <h2>Your Generated Question Papers</h2>
        <div className="papers-grid">
          {questionPapers.map((paper, index) => (
            <div className="paper-card" key={index}>
              <h3>{paper.title}</h3>
              <p><strong>Subject:</strong> {paper.subject}</p>
              <p><strong>Date:</strong> {paper.date}</p>
              <a
                href={`/${paper.file}`}
                target="_blank"
                rel="noopener noreferrer"
              >
                View Paper
              </a>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default QuestionPapers;
