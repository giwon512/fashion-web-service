import React, { useEffect, useState } from "react";
import api from "../../api";
import { Bar } from "react-chartjs-2";
import "chart.js/auto";
import "./ManageSurveys.css";

const ManageSurveys = () => {
    const [surveys, setSurveys] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchSurveys = async () => {
            try {
                const response = await api.get("/surveys");
                setSurveys(response.data.body);
                setLoading(false);
            } catch (error) {
                console.error("Failed to fetch surveys:", error);
                setLoading(false);
            }
        };

        fetchSurveys();
    }, []);

    const genderCounts = surveys.reduce(
        (acc, survey) => {
            const gender = survey.userSurvey.gender;
            acc[gender] = (acc[gender] || 0) + 1;
            return acc;
        },
        {}
    );

    const ageGroupCounts = surveys.reduce(
        (acc, survey) => {
            const ageGroup = survey.userSurvey.ageGroup;
            acc[ageGroup] = (acc[ageGroup] || 0) + 1;
            return acc;
        },
        {}
    );

    const styleCounts = surveys.reduce((acc, survey) => {
        survey.styles.forEach(style => {
            acc[style.name] = (acc[style.name] || 0) + 1;
        });
        return acc;
    }, {});

    const brandCounts = surveys.reduce((acc, survey) => {
        survey.brands.forEach(brand => {
            acc[brand.name] = (acc[brand.name] || 0) + 1;
        });
        return acc;
    }, {});

    const createChartData = (data, label) => ({
        labels: Object.keys(data),
        datasets: [
            {
                label: label,
                data: Object.values(data),
                backgroundColor: [
                    "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40",
                ],
            },
        ],
    });

    const genderData = createChartData(genderCounts, "Gender Distribution");
    const ageGroupData = createChartData(ageGroupCounts, "Age Group Distribution");
    const styleData = createChartData(styleCounts, "Style Preferences");
    const brandData = createChartData(brandCounts, "Brand Preferences");

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="manage-surveys">
            <h2>User Surveys Dashboard</h2>
            <div className="charts">
                <div className="chart">
                    <h3>Gender Distribution</h3>
                    <Bar data={genderData} />
                </div>
                <div className="chart">
                    <h3>Age Group Distribution</h3>
                    <Bar data={ageGroupData} />
                </div>
                <div className="chart">
                    <h3>Style Preferences</h3>
                    <Bar data={styleData} />
                </div>
                <div className="chart">
                    <h3>Brand Preferences</h3>
                    <Bar data={brandData} />
                </div>
            </div>
            <div className="survey-list">
                <h3>Survey Details</h3>
                <table className="survey-table">
                    <thead>
                    <tr>
                        <th>Survey ID</th>
                        <th>User ID</th>
                        <th>Gender</th>
                        <th>Age Group</th>
                        <th>Styles</th>
                        <th>Brands</th>
                    </tr>
                    </thead>
                    <tbody>
                    {surveys.map((survey) => (
                        <tr key={survey.userSurvey.surveyId}>
                            <td>{survey.userSurvey.surveyId}</td>
                            <td>{survey.userSurvey.userId}</td>
                            <td>{survey.userSurvey.gender}</td>
                            <td>{survey.userSurvey.ageGroup}</td>
                            <td>{survey.styles.map((style) => style.name).join(", ")}</td>
                            <td>{survey.brands.map((brand) => brand.name).join(", ")}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ManageSurveys;