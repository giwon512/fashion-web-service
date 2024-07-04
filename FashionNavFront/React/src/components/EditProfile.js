import React, { useState, useEffect } from 'react';
import api from '../api';
import './EditProfile.css';

const EditProfile = () => {
    const [user, setUser] = useState(null);
    const [isGoogleUser, setIsGoogleUser] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        gender: 'male',
        phoneNumber: '',
        birthdate: '',
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
    });

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await api.get('/users/me');
                const userData = response.data.body;
                setUser(userData);

                // Console log to check the retrieved data
                console.log("Fetched user data: ", userData);

                setFormData({
                    name: userData.name || '',
                    email: userData.email || '',
                    gender: userData.gender || 'male',
                    phoneNumber: userData.phoneNumber || '',
                    birthdate: userData.birthdate || '',
                    currentPassword: '',
                    newPassword: '',
                    confirmPassword: ''
                });

                // 구글 사용자 여부를 서버에서 받아온 정보로 설정
                setIsGoogleUser(userData.googleUser);
                console.log("isGoogleUser: ", userData.googleUser); // 확인용 로그
            } catch (error) {
                console.error('Failed to fetch user info', error);
                alert('Failed to fetch user info: ' + (error.response?.data?.result?.resultMessage || 'Unknown error'));
            }
        };

        fetchUser();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!isGoogleUser && formData.newPassword !== formData.confirmPassword) {
            alert('New password and confirmation do not match');
            return;
        }
        try {
            const requestData = {
                name: formData.name,
                email: formData.email,
                gender: formData.gender,
                phoneNumber: formData.phoneNumber,
                birthdate: formData.birthdate,
                googleUser: isGoogleUser // 구글 사용자 여부를 포함
            };

            if (!isGoogleUser) {
                if (formData.currentPassword) requestData.currentPassword = formData.currentPassword;
                if (formData.newPassword) requestData.newPassword = formData.newPassword;
            }

            await api.put('/users/me', requestData);
            alert('Profile updated successfully');
        } catch (error) {
            console.error('Failed to update profile', error);
            alert('Failed to update profile: ' + (error.response?.data?.result?.resultMessage || 'Unknown error'));
        }
    };

    return (
        <div className="edit-profile-container">
            <h2>Edit Profile</h2>
            {user && (
                <form onSubmit={handleSubmit} className="edit-profile-form">
                    <div className="form-group">
                        <label>Name</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Gender</label>
                        <select
                            name="gender"
                            value={formData.gender}
                            onChange={handleChange}
                            required
                        >
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Phone Number</label>
                        <input
                            type="text"
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            placeholder="010-1234-5678"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Birthdate</label>
                        <input
                            type="date"
                            name="birthdate"
                            value={formData.birthdate}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    {!isGoogleUser && (
                        <>
                            <div className="form-group">
                                <label>Current Password</label>
                                <input
                                    type="password"
                                    name="currentPassword"
                                    value={formData.currentPassword}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label>New Password</label>
                                <input
                                    type="password"
                                    name="newPassword"
                                    value={formData.newPassword}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="form-group">
                                <label>Confirm New Password</label>
                                <input
                                    type="password"
                                    name="confirmPassword"
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                />
                            </div>
                        </>
                    )}
                    <button type="submit">Update Profile</button>
                </form>
            )}
        </div>
    );
};

export default EditProfile;