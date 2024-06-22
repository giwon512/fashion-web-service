import React, { useState, useEffect } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";
import "./SubmitSurvey.css";

const SubmitSurvey = ({ onLogin }) => {
    const [gender, setGender] = useState("");
    const [ageGroup, setAgeGroup] = useState("");
    const [styles, setStyles] = useState([]);
    const [brands, setBrands] = useState([]);
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserId = async () => {
            try {
                const response = await api.get("/users/me");
                console.log(response.data);
                setUserId(response.data.body.userId);
            } catch (error) {
                console.error("Error fetching user ID", error);
            }
        };
        fetchUserId();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!userId) {
            alert("User ID is not available");
            return;
        }

        const request = {
            userSurvey: {
                userId,
                gender,
                ageGroup,
            },
            styleIds: styles,
            brandIds: brands,
        };

        try {
            await api.post("/surveys", request);
            alert("Survey submitted successfully");
            await onLogin();  // 로그인 상태를 업데이트
            navigate("/");
        } catch (error) {
            console.error("Error submitting survey", error);
            alert("Failed to submit survey");
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
        <form onSubmit={handleSubmit} className="survey-form">
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
                    {[
                        { id: "1", name: "미니멀" },
                        { id: "2", name: "클래식" },
                        { id: "3", name: "스트릿" },
                        { id: "4", name: "아메카지" },
                        { id: "5", name: "캐쥬얼" },
                        { id: "6", name: "빈티지" },
                    ].map((style) => (
                        <button
                            key={style.id}
                            type="button"
                            className={`option ${styles.includes(style.id) ? "selected" : ""}`}
                            onClick={() => handleToggle(style.id, setStyles, styles)}
                        >
                            {style.name}
                        </button>
                    ))}
                </div>
            </div>
            <div className="form-group">
                <label>브랜드:</label>
                <div className="options">
                    {[
                        { id: "1", name: "구찌" },
                        { id: "2", name: "나이키" },
                        { id: "3", name: "자라" },
                        { id: "4", name: "폴로" },
                        { id: "5", name: "스투시" },
                    ].map((brand) => (
                        <button
                            key={brand.id}
                            type="button"
                            className={`option ${brands.includes(brand.id) ? "selected" : ""}`}
                            onClick={() => handleToggle(brand.id, setBrands, brands)}
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

export default SubmitSurvey;
