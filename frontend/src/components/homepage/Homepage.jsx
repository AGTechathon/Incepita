import React from 'react';
import './Homepage.css';
import Features from '../features/Features';
import Workflow from '../workflow/Workflow';

const Homepage = () => {
  const scrollToFeatures = () => {
    const featuresSection = document.getElementById('features');
    if (featuresSection) {
      featuresSection.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <>
      <div className="homepage-container">
        <div className="overlay"></div>
        <section className="intro-section">
          <h1 className="intro-title">Welcome to <span>NextGenPaper</span></h1>
          <p className="intro-description">
            Redefining documentation for the digital era — secure, lightning-fast, and sustainable.
          </p>
          <p className="intro-sub">
            Say goodbye to paper jams, and hello to smarter, greener workflows.
          </p>
          <div className="button-group">
            <button className="cta-button">Get Started</button>
            <button className="secondary-button" onClick={scrollToFeatures}>
              Explore Features ↓
            </button>
          </div>
        </section>
      </div>

      {/* These are part of the same page */}
      <Features />
      <Workflow />
    </>
  );
};

export default Homepage;
