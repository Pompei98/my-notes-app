import React from 'react';

function EditNote ({ selectedNote, setSelectedNote }) {
    return (
        <div className="edit-note-card">
                <input
                        className="title-input"
                        type="text"
                        value={selectedNote.titolo || ''}
                        onChange={(e) =>
                            setSelectedNote({ ...selectedNote, titolo: e.target.value })
                        }
                        placeholder='Titolo'
                    />
                <textarea
                        className="text-input"
                        value={selectedNote.testo || ''}
                        onChange={(e) =>
                            setSelectedNote({ ...selectedNote, testo: e.target.value })
                    }
                    placeholder='Testo'
                />
        </div>
    );
}

export default EditNote;