import React, { useState, useEffect } from 'react';
import api from '../api';
import { useNavigate, useParams } from 'react-router-dom';

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



    return (
        <form onSubmit={handleUpdate} className="survey-form">
            <label>
                성별:
                <select value={gender} onChange={(e) => setGender(e.target.value)}>
                    <option value="">선택하세요</option>
                    <option value="남">남</option>
                    <option value="여">여</option>
                </select>
            </label>
            <br />
            <label>
                연령대:
                <select value={ageGroup} onChange={(e) => setAgeGroup(e.target.value)}>
                    <option value="">선택하세요</option>
                    <option value="10대">10대</option>
                    <option value="20대">20대</option>
                    <option value="30대">30대</option>
                    <option value="40대">40대</option>
                </select>
            </label>
            <br />
            <label>
                스타일:
                <select multiple value={styles} onChange={(e) => setStyles([...e.target.selectedOptions].map(o => parseInt(o.value)))}>
                    {availableStyles.map((style) => (
                        <option key={style.styleId} value={style.styleId}>{style.name}</option>
                    ))}
                </select>
            </label>
            <br />
            <label>
                브랜드:
                <select multiple value={brands} onChange={(e) => setBrands([...e.target.selectedOptions].map(o => parseInt(o.value)))}>
                    {availableBrands.map((brand) => (
                        <option key={brand.brandId} value={brand.brandId}>{brand.name}</option>
                    ))}
                </select>
            </label>
            <br />
            <button type="submit">Update</button>
        </form>
    );
};

export default EditSurvey;
