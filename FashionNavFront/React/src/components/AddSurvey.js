import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';
import './AddSurvey.css';

const AddSurvey = () => {
    const [gender, setGender] = useState('');
    const [ageGroup, setAgeGroup] = useState('');
    const [styles, setStyles] = useState('');
    const [brands, setBrands] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleAddSurvey = async (e) => {
        e.preventDefault();
        setError('');

        try {
            await api.post('/surveys', {
                gender,
                ageGroup,
                styles: styles.split(',').map(style => style.trim()),
                brands: brands.split(',').map(brand => brand.trim())
            });
            navigate('/survey-management');
        } catch (error) {
            console.error('Error adding survey', error);
            setError('Failed to add survey. Please try again.');
        }
    };

    return (
        <div className="add-survey-container">
            <h1>설문 추가</h1>
            <form onSubmit={handleAddSurvey} className="add-survey-form">
                <div className="form-group">
                    <label htmlFor="gender">성별</label>
                    <input
                        type="text"
                        id="gender"
                        value={gender}
                        onChange={(e) => setGender(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="ageGroup">연령대</label>
                    <input
                        type="text"
                        id="ageGroup"
                        value={ageGroup}
                        onChange={(e) => setAgeGroup(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="styles">스타일 (콤마로 구분)</label>
                    <input
                        type="text"
                        id="styles"
                        value={styles}
                        onChange={(e) => setStyles(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="brands">브랜드 (콤마로 구분)</label>
                    <input
                        type="text"
                        id="brands"
                        value={brands}
                        onChange={(e) => setBrands(e.target.value)}
                    />
                </div>
                {error && <p className="error">{error}</p>}
                <button type="submit" className="add-survey-button">설문 추가</button>
            </form>
        </div>
    );
};

export default AddSurvey;
