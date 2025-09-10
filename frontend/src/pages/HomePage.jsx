import './HomePage.css';
import Card from './Card';
import EditNote from './EditNote';
import React, { useEffect, useState, useRef } from 'react';
import { FaSave, FaArrowLeft, FaPlus } from "react-icons/fa";


function HomePage() {
    const apiUrl = import.meta.env.VITE_API_URL;
    const [notes, setNotes] = useState([]);
    const [selectedNote, setSelectedNote] = useState(null); // nota selezionata
    const [isEditing, setIsEditing] = useState(false); // mostra/nascondi editor
    const newNoteRef = useRef(null);
    const notesRef = useRef([]); // per evitare l'animazione al primo render
    const handleClick = (nota) => {
        if (nota == null) {
            setSelectedNote({ titolo: '', testo: '' });
            setIsEditing(true);
            return;
        }
        setSelectedNote(nota);
        setIsEditing(true);
    };

    const deleteClick = (nota) => {
        fetch(`${apiUrl}/note/${nota.idNota}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(res => {
                if (!res.ok) throw new Error('Errore eliminazione');
            })
            .then(() => {
                if (nota.idNota == selectedNote.idNota) {
                    setIsEditing(false);
                }
                setNotes(prevNotes => prevNotes.filter(n => n.idNota !== nota.idNota))

            })

    }

    const handleClickSave = () => {
        if (!selectedNote) return;
        const method = selectedNote.idNota ? 'PUT' : 'POST';
        const url = selectedNote.idNota ? `${process.env.REACT_APP_API_URL}/note/${selectedNote.idNota}` : `${process.env.REACT_APP_API_URL}/note`
        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(selectedNote)
        })
            .then(res => {
                if (!res.ok) throw new Error('Errore salvataggio');
                return res.json();
            })
            .then(savedNote => {
                setNotes(prevNotes => {
                    if (method === 'POST') {
                        setSelectedNote(savedNote); // aggiorna selectedNote con idNota
                        return [...prevNotes, savedNote]; // aggiunge nuova nota
                    } else {
                        console.log(savedNote.idNota);
                        return prevNotes.map(n =>
                            n.idNota === savedNote.idNota ? savedNote : n
                        ); // aggiorna nota esistente
                    }
                });
            })

    }


    useEffect(() => {
        fetch(`${apiUrl}/note`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(res => res.json())
            .then(data => setNotes(data))
            .then(data => console.log(data))
            .catch(err => console.error(err));
    }, [])

    useEffect(() => {
        if (notesRef.current.length === 0) {
            notesRef.current = notes;
            return; // salta la prima esecuzione
        }

        if (newNoteRef.current && notes.length > notesRef.current.length) {
            notesRef.current = notes;
            newNoteRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' });

            newNoteRef.current.classList.add('highlight');
            const timer = setTimeout(() => {
                newNoteRef.current.classList.remove('highlight');
                newNoteRef.current = null;
            }, 1000);

            return () => clearTimeout(timer);
        }
    }, [notes])



    return (
        <div className="home-page">
            <p className="title-bar-home">My Notes</p>
            <button className="add-button" onClick={() => handleClick(null)}><FaPlus /></button>
            <div className="main-container">
                <div className="note-container">
                    {notes.map((nota, index) => (
                        < Card key={nota.idNota}
                            title={nota.titolo}
                            text={nota.testo}
                            onSelect={() => handleClick(nota)}
                            onDelete={() => deleteClick(nota)}
                            ref={index === notes.length - 1 ? newNoteRef : null} />
                    ))}

                </div>
                {isEditing && (
                    <div className="screen-editor">
                        <div className="bottom-bar">
                            <button onClick={() => setIsEditing(false)} className="close-button"><FaArrowLeft /></button>
                            <button onClick={() => handleClickSave()} className="save-button" disabled={selectedNote.titolo.trim() === '' && selectedNote.testo.trim() === ''}><FaSave /></button>
                        </div>
                        <EditNote
                            selectedNote={selectedNote}
                            setSelectedNote={setSelectedNote}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}


export default HomePage;