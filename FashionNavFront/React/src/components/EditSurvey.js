import React, { useState, useEffect } from 'react';
import api from '../api';
import { useNavigate, useParams } from 'react-router-dom';
import "./SubmitSurvey.css";

const EditSurvey = () => {
    const { surveyId } = useParams();
    const navigate = useNavigate();
    const [gender, setGender] = useState('');
    const [ageGroup, setAgeGroup] = useState('');
    const [styles, setStyles] = useState([]);
    const [brands, setBrands] = useState([]);
    const [availableStyles, setAvailableStyles] = useState([]);
    const [availableBrands, setAvailableBrands] = useState([]);

    useEffect(() => {
        const fetchSurvey = async () => {
            try {
                const response = await api.get(`/surveys/${surveyId}`);
                const survey = response.data.body;
                setGender(survey.userSurvey.gender);
                setAgeGroup(survey.userSurvey.ageGroup);
                setStyles(survey.styles.map(style => style.styleId));
                setBrands(survey.brands.map(brand => brand.brandId));
            } catch (error) {
                console.error('Failed to fetch survey', error);
            }
        };

        const fetchOptions = async () => {
            try {
                const [stylesResponse, brandsResponse] = await Promise.all([
                    api.get('/styles'),
                    api.get('/brands'),
                ]);
                setAvailableStyles(stylesResponse.data.body);
                setAvailableBrands(brandsResponse.data.body);
            } catch (error) {
                console.error('Failed to fetch options', error);
            }
        };

        fetchSurvey();
        fetchOptions();
    }, [surveyId]);

    const handleUpdate = async (e) => {
        e.preventDefault();

        const request = {
            userSurvey: {
                gender,
                ageGroup,
            },
            styleIds: styles,
            brandIds: brands,
        };

        try {
            await api.put(`/surveys/${surveyId}`, request);
            alert('Survey updated successfully');
            navigate('/survey-management');
        } catch (error) {
            console.error('Failed to update survey', error);
            alert('Failed to update survey');
        }
    };

    const handleToggle = (id, setState, state) => {
        if (state.includes(id)) {
            setState(state.filter((item) => item !== id));
        } else {
            setState([...state, id]);
        }
    };

    return (
        <form onSubmit={handleUpdate} className="survey-form">
            <div className="form-group">
                <label>성별:</label>
                <div className="options">
                    <button
                        type="button"
                        className={`option ${gender === "남" ? "selected" : ""}`}
                        onClick={() => setGender("남")}
                    >
                        남
                    </button>
                    <button
                        type="button"
                        className={`option ${gender === "여" ? "selected" : ""}`}
                        onClick={() => setGender("여")}
                    >
                        여
                    </button>
                </div>
            </div>
            <div className="form-group">
                <label>연령대:</label>
                <div className="options">
                    {["10대", "20대", "30대", "40대"].map((age) => (
                        <button
                            key={age}
                            type="button"
                            className={`option ${ageGroup === age ? "selected" : ""}`}
                            onClick={() => setAgeGroup(age)}
                        >
                            {age}
                        </button>
                    ))}
                </div>
            </div>
            <div className="form-group">
                <label>스타일:</label>
                <div className="options">
                    {availableStyles.map((style) => (
                        <button
                            key={style.styleId}
                            type="button"
                            className={`option ${styles.includes(style.styleId) ? "selected" : ""}`}
                            onClick={() => handleToggle(style.styleId, setStyles, styles)}
                        >
                            {style.name}
                        </button>
                    ))}
                </div>
            </div>
            <div className="form-group">
                <label>브랜드:</label>
                <div className="options">
                    {availableBrands.map((brand) => (
                        <button
                            key={brand.brandId}
                            type="button"
                            className={`option ${brands.includes(brand.brandId) ? "selected" : ""}`}
                            onClick={() => handleToggle(brand.brandId, setBrands, brands)}
                        >
                            {brand.name}
                        </button>
                    ))}
                </div>
            </div>
            <button type="submit" className="submit-button">저장</button>
        </form>
    );
};

export default EditSurvey;
