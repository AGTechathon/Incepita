import React from 'react';
import './Workflow.css';

const steps = [
  {
    step: "01",
    title: "Requirement Gathering",
    description:
      "Teacher uploads curriculum, sets schema, difficulty, and number of sets.",
    icon: "📂",
  },
  {
    step: "02",
    title: "Question Generation",
    description:
      "ChatGPT generates questions and categorizes them using Bloom’s Taxonomy.",
    icon: "✨",
  },
  {
    step: "03",
    title: "Review & Edit",
    description:
      "Teacher reviews, makes changes, and regenerates any weak questions.",
    icon: "✏️",
  },
  {
    step: "04",
    title: "Export Paper",
    description:
      "Download the final paper in PDF or DOC format.",
    icon: "📤",
  },
];

const Workflow = () => {
  return (
    <div className="timeline-container" id="workflow">
      <h2 className="workflow-heading">How It Works</h2>
      <div className="timeline">
        {steps.map((step, index) => (
          <div
            className={`timeline-item ${index % 2 === 0 ? 'left' : 'right'}`}
            key={index}
          >
            <div className="timeline-content">
              <span className="step-icon">{step.icon}</span>
              <h3 className="step-title">{step.title}</h3>
              <p className="step-description">{step.description}</p>
              <div className="step-circle">{step.step}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Workflow;
