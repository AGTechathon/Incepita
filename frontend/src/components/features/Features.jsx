import React from 'react';
import './Features.css';

const featuresData = [
  {
    title: "Bloom's Taxonomy Support",
    description:
      "Generate questions based on cognitive levels like remembering, understanding, applying, and analyzing — ensuring depth and diversity in assessments.",
    icon: "🧠",
  },
  {
    title: "Curriculum-Aligned Generation",
    description:
      "Upload your syllabus or curriculum file and the system will auto-align generated questions with specific learning outcomes.",
    icon: "📚",
  },
  {
    title: "Multi-Set Paper Generation",
    description:
      "Automatically create shuffled sets (Set A, B, C) of the same paper to reduce chances of cheating and improve fairness.",
    icon: "🌀",
  },
];

const Features = () => {
  return (
    <div className="features-container" id="features">
      <h2 className="features-heading">Key Features of NextGenPaper</h2>
      <div className="cards-wrapper">
        {featuresData.map((feature, index) => (
          <div
            className="feature-card"
            key={index}
            style={{ '--i': index }}
          >
            <div className="icon">{feature.icon}</div>
            <h3 className="feature-title">{feature.title}</h3>
            <p className="feature-description">{feature.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Features;
