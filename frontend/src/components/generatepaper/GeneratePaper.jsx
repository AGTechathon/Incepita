import React, { useState } from 'react';
import './GeneratePaper.css';
import Sidebar from '../sidebar/Sidebar.jsx';
import { useParams, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const bloomLevels = ['Remember', 'Understand', 'Apply', 'Analyze', 'Evaluate', 'Create'];

const GeneratePaper = () => {

    const navigate = useNavigate();
    const { username } = useParams();


    useEffect(() => {
        const storedUser = localStorage.getItem("username");
        const token = localStorage.getItem("token");

        if (!token || storedUser !== username) {
            navigate("/login");
        }
    }, [username, navigate]);


    const [curriculumFile, setCurriculumFile] = useState(null);
    const [paperFormatFile, setPaperFormatFile] = useState(null);
    const [weights, setWeights] = useState(Array(6).fill(0));
    const [error, setError] = useState('');


    const handleFileChange = (e, setFile) => {
        const file = e.target.files[0];
        if (file && file.type === 'application/pdf') {
            setFile(file);
        } else {
            alert('Only PDF files are allowed');
        }
    };

    const handleWeightChange = (index, value) => {
        const parsed = parseInt(value, 10);
        const inputVal = isNaN(parsed) ? 0 : parsed;

        const totalWithoutCurrent = weights.reduce(
            (acc, curr, i) => acc + (i === index ? 0 : curr),
            0
        );
        const maxAllowed = 100 - totalWithoutCurrent;

        const newWeights = [...weights];

        if (inputVal > maxAllowed) {
            setError(`Total sum should not exceed 100`);
            newWeights[index] = maxAllowed;
        } else {
            setError('');
            newWeights[index] = inputVal;
        }

        setWeights(newWeights);
    };

    return (
        <div className="page-wrapper">

            <Sidebar />

            <div className="generate-paper-container-with-sidebar">
                <div className="upload-section">
                    <div className="upload-box">
                        <label className="upload-label">Curriculum</label>
                        <input
                            type="file"
                            accept="application/pdf"
                            onChange={(e) => handleFileChange(e, setCurriculumFile)}
                        />
                    </div>

                    <div className="upload-box">
                        <label className="upload-label">Paper Format</label>
                        <input
                            type="file"
                            accept="application/pdf"
                            onChange={(e) => handleFileChange(e, setPaperFormatFile)}
                        />
                    </div>
                </div>

                <div className="table-section">
                    <table className="bloom-table">
                        <thead>
                            <tr>
                                <th>Bloom Level</th>
                                <th>Weightage (%)</th>
                            </tr>
                        </thead>
                        <tbody>
                            {bloomLevels.map((level, index) => (
                                <tr key={index}>
                                    <td>{level}</td>
                                    <td>
                                        <input
                                            type="number"
                                            min="0"
                                            max="100"
                                            value={weights[index]}
                                            onChange={(e) => handleWeightChange(index, e.target.value)}
                                        />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    {error && <p className="error-text">{error}</p>}

                    <button
                        className="generate-button"
                        onClick={() => {
                            console.log("Generating paper...");
                            
                            
                        }}
                    >
                        Generate Paper
                    </button>
                </div>
            </div>
        </div>
    );
};

export default GeneratePaper;
