import React from 'react';
// import ReactDOM from 'react-dom';
import { createRoot } from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom'; 
import App from './App';

createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Router>
            <App />
        </Router>
    </React.StrictMode>
);