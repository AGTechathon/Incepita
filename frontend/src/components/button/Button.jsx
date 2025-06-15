// src/components/Button.jsx
const Button = ({ text, onClick, type = "button", style = {} }) => {
 

  const buttonStyle = {
  backgroundColor: "#6C63FF",
  color: "#FFFFFF",
  border: "none",
  padding: "0.5rem 1rem",
  borderRadius: "8px",
  fontWeight: "bold",
  cursor: "pointer",
  transition: "background-color 0.3s",
};

const hoverStyle = {
  backgroundColor: "#574FCF",
};


  return (
    <button type={type} onClick={onClick} style={buttonStyle}>
      {text}
    </button>
  );
};

export default Button;
