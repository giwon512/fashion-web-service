import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import './App.css';
import MainPage from './Components/MainPage';
import NewsPage from './Components/newsPage';

function App() {
    return (
        <Router>
            <div className='app'>
                <Switch>

                    <Route path="/" exact>
                        <MainPage />
                    </Route>

                    <Route path="/newsPage">
                        <NewsPage />
                    </Route>
                    
                </Switch>
            </div>
        </Router>
    );
}

export default App;