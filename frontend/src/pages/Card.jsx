import React, { forwardRef } from 'react';
import './HomePage.css';
import { FaTrash } from "react-icons/fa";

const Card = forwardRef(({ title, text, onSelect, onDelete }, ref) => {
    return (
        <div className="note-card" onClick={onSelect} ref={ref}>
            <div className="note-title-bar">
                <h3 className="note-title">{title}</h3>
                <button className="delete-button" onClick={(e) => {
                    e.stopPropagation();
                    onDelete()
                }}>
                    <FaTrash />
                </button>
            </div>
            <p className="note-content">{text}</p>
        </div>
    );
});

export default Card;