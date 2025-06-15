import { useParams } from "react-router-dom";
import React, { useEffect, useState, version } from "react";
import "./SingleQuestion.css";
import Sidebar from "../sidebar/Sidebar";
import { useNavigate } from 'react-router-dom';


const bloomsLevels = [
  "Remember",
  "Understand",
  "Apply",
  "Analyze",
  "Evaluate",
  "Create",
];

const SingleQuestion = () => {

  useNavigate();

  const { username, questionPaperId, targetVersion } = useParams();
  const [questions, setQuestions] = useState([]);
  const [error, setError] = useState(null);
  const [paperName, setPaperName] = useState("");
  const [loading, setLoading] = useState([]);

  useEffect(() => {
    const fetchQuestionPaper = async () => {
      try {
        const url = `http://localhost:8080/api/question-paper/get/${username}/${questionPaperId}/${targetVersion}`;
        const token = localStorage.getItem("token");

        const response = await fetch(url, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) throw new Error(`API error: ${response.status}`);
        const data = await response.json();
        setQuestions(data.questionList);
        setPaperName(data.paperName);
        setLoading(new Array(data.questionList.length).fill(false));
      } catch (err) {
        setError(err.message);
        console.error(err);
      }
    };

    fetchQuestionPaper();
  }, [username, questionPaperId, targetVersion]);

  const setLoadingAtIndex = (index, value) => {
    setLoading((prev) => {
      const updated = [...prev];
      updated[index] = value;
      return updated;
    });
  };

  const handleInputChange = (index, field, value) => {
    const updated = [...questions];
    updated[index][field] = value;
    setQuestions(updated);
  };

  const handlePaperChange = (name) => {
    setPaperName(name);
    updatePaperName(name, questionPaperId);
  };

  async function updatePaperName(newName, questionPaperId) {
    const token = localStorage.getItem("token");

    try {
      const response = await fetch(
        `http://localhost:8080/api/question-paper/update-paper-name/${newName}/${questionPaperId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to update paper name");
      }
    } catch (error) {
      console.error("Error updating paper name:", error);
    }
  }

  async function regenerateQuestion(
    questionPaperId,
    questionId,
    currQuestion,
    bloomLevel
  ) {
    const url = `http://localhost:8080/api/generate/question/`;
    const token = localStorage.getItem("token");

    const payload = {
      questionPaperId,
      questionId,
      currQuestion,
      bloomLevel,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      throw new Error("Failed to regenerate question");
    }

    const data = await response.json(); 
    // Navigate(`/${username}/papers/${questionPaperId}/${data.versionNo}`);
    
    return data;
  }

  async function updateQuestion(payload) {
    let url = `http://localhost:8080/api/question/update`;
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        throw new Error("Failed to update question");
      }

      console.log("Question updated successfully");
    } catch (err) {
      console.error(err.message);
    }
  }

  const handleRegenerate = async (index) => {
    try {
      setLoadingAtIndex(index, true);
      const question = questions[index];
      const regenerated = await regenerateQuestion(
        questionPaperId,
        question.questionId,
        question.question,
        question.bloomlevel
      );

      const updated = [...questions];
      updated[index].question = regenerated.question;
      updated[index].bloomlevel = regenerated.bloomlevel;
      setQuestions(updated);
    } catch (error) {
      console.error("Regeneration failed:", error);
    } finally {
      setLoadingAtIndex(index, false);
    }
  };

  const handleSave = async (index) => {
    const question = questions[index];
    const payload = {
      questionId: question.questionId,
      questionPaperId: questionPaperId,
      question: question.question,
      bloomLevel: question.bloomlevel,
    };
    console.log("Saving question:", payload);
    await updateQuestion(payload);
  };

  const deleteQuestionPaper = async () => {
    const token = localStorage.getItem("token");

    try {
      const response = await fetch(
        `http://localhost:8080/api/question-paper/delete/${username}/${questionPaperId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to delete question paper");
      }

      alert("Question paper deleted successfully");

      window.location.href = `/${username}/papers`;
    } catch (error) {
      console.log("Delete error:", error);
      alert("Error deleting question paper");
    }
  };

  async function getShuffledQuestionPaper(
    username,
    questionPaperId,
    targetVersion
  ) {
    const token = localStorage.getItem("token");
    const url = `http://localhost:8080/api/question-paper/shuffle/${username}/${questionPaperId}/${targetVersion}`;
    console.log(token);

    try {
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Failed to shuffle and export question paper");
      }

      const blob = await response.blob();
      const urlBlob = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = urlBlob;
      link.download = paperName + ".pdf";
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(urlBlob);
    } catch (error) {
      console.error("Export failed:", error);
      alert("Failed to export paper");
    }
  }

  return (
    <div className="mainContainer">
      <Sidebar />

      <div className="singlequestion-wrapper">
        <div className="paper-name-container">
          <div>
            <label htmlFor="paperName">Paper Name:</label>
            <input
              type="text"
              id="paperName"
              value={paperName}
              onChange={(e) => handlePaperChange(e.target.value)}
              placeholder="Enter paper name"
              className="paper-name-input"
            />
          </div>
          <button
            type="button"
            className="singlequestion-action-btn"
            onClick={() =>
              getShuffledQuestionPaper(username, questionPaperId, targetVersion)
            }
          >
            Export Paper
          </button>
        </div>

        {error && <p style={{ color: "red" }}>{error}</p>}

        {questions.map((q, index) => (
          <form
            key={q.questionId}
            className="singlequestion-container"
            onSubmit={(e) => e.preventDefault()}
          >
            <textarea
              value={q.question}
              onChange={(e) =>
                handleInputChange(index, "question", e.target.value)
              }
              className="mypaper-input"
              rows={4}
              style={{
                width: "600px",
                resize: "vertical",
                whiteSpace: "pre-wrap",
              }}
            />

            <select
              className="singlequestion-dropdown"
              value={q.bloomlevel}
              onChange={(e) =>
                handleInputChange(index, "bloomlevel", e.target.value)
              }
            >
              <option value="">Select Level</option>
              {bloomsLevels.map((level, idx) => (
                <option key={idx} value={level}>
                  {level}
                </option>
              ))}
            </select>

            <div className="singlequestion-actions">
              <button
                type="button"
                className="singlequestion-action-btn"
                onClick={() => handleRegenerate(index)}
                disabled={loading[index]}
              >
                {loading[index] ? "Regenerating..." : "Regenerate"}
              </button>

              <button
                type="submit"
                className="singlequestion-action-btn"
                onClick={() => handleSave(index)}
              >
                Save
              </button>
            </div>
          </form>
        ))}

        <div className="paper-delete-container">
          <button
            className="delete-paper-btn"
            onClick={() => deleteQuestionPaper()}
          >
            Delete Paper
          </button>
        </div>
      </div>
    </div>
  );
};

export default SingleQuestion;
