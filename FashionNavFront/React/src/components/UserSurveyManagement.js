import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import './UserSurveyManagement.css';
import SubmitSurvey from './SubmitSurvey'; // SubmitSurvey 컴포넌트 import

const UserSurveyManagement = () => {
    const [surveys, setSurveys] = useState([]);
    const [showSubmitSurvey, setShowSubmitSurvey] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSurveys = async () => {
            try {
                const response = await api.get('/surveys/user');
                const surveysWithDefaults = response.data.body.map(survey => ({
                    ...survey,
                    styles: survey.styles || [],
                    brands: survey.brands || []
                }));
                setSurveys(surveysWithDefaults);
            } catch (error) {
                console.error('Failed to fetch user surveys', error);
            }
        };
        fetchSurveys();
    }, []);

    const handleDeleteSurvey = async (surveyId) => {
        if (window.confirm('정말 이 설문을 삭제하시겠습니까?')) {
            try {
                await api.delete(`/surveys/${surveyId}`);
                setSurveys(surveys.filter(survey => survey.userSurvey.surveyId !== surveyId));
            } catch (error) {
                console.error('Error deleting survey', error);
            }
        }
    };

    const handleAddSurvey = () => {
        setShowSubmitSurvey(true); // SubmitSurvey 컴포넌트 표시
    };

    return (
        <div className="survey-management-container">
            <h1>설문 관리</h1>
            {surveys.length > 0 ? (
                <ul>
                    {surveys.map((survey) => (
                        <li key={survey.userSurvey.surveyId}>
                            <p>성별: {survey.userSurvey.gender}</p>
                            <p>연령대: {survey.userSurvey.ageGroup}</p>
                            <p>스타일: {survey.styles.length > 0 ? survey.styles.map(style => style.name).join(', ') : 'N/A'}</p>
                            <p>브랜드: {survey.brands.length > 0 ? survey.brands.map(brand => brand.name).join(', ') : 'N/A'}</p>
                            <Link to={`/edit-survey/${survey.userSurvey.surveyId}`}>수정</Link>
                            <button onClick={() => handleDeleteSurvey(survey.userSurvey.surveyId)}>삭제</button>
                        </li>
                    ))}
                </ul>
            ) : (
                <div className="no-surveys">
                    <p>설문 내역이 없습니다. 설문을 시작하시겠습니까?</p>
                    <button onClick={handleAddSurvey} className="start-survey-button">예</button>
                </div>
            )}
            {showSubmitSurvey && (
                <SubmitSurvey onLogin={() => navigate('/survey-management')} />
            )}
        </div>
    );
};

export default UserSurveyManagement;
