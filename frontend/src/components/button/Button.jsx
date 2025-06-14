// src/components/Button.jsx
const Button = ({ text, onClick, type = "button", style = {} }) => {
  const defaultStyle = {
    padding: "10px 20px",
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "16px",
    
  };

  return (
    <button type={type} onClick={onClick} style={defaultStyle}>
      {text}
    </button>
  );
};

export default Button;
