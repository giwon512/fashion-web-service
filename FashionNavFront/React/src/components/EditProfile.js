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
    });
    const [passwordData, setPasswordData] = useState({
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
    });
    const [phoneError, setPhoneError] = useState(''); // 전화번호 에러 메시지 상태 추가

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await api.get('/users/me');
                const userData = response.data.body;
                setUser(userData);

                setFormData({
                    name: userData.name || '',
                    email: userData.email || '',
                    gender: userData.gender || 'male',
                    phoneNumber: formatPhoneNumber(userData.phoneNumber) || '',
                    birthdate: userData.birthdate || '',
                });

                setIsGoogleUser(userData.googleUser);
            } catch (error) {
                console.error('Failed to fetch user info', error);
                alert('Failed to fetch user info: ' + (error.response?.data?.result?.resultMessage || 'Unknown error'));
            }
        };

        fetchUser();
    }, []);

    const formatPhoneNumber = (phoneNumber) => {
        if (!phoneNumber) return '';
        return phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    };

    const handlePhoneNumberChange = (event) => {
        let input = event.target.value.replace(/[^0-9]/g, '');

        if (input.length <= 3) {
            setFormData((prevData) => ({
                ...prevData,
                phoneNumber: input
            }));
        } else if (input.length <= 7) {
            setFormData((prevData) => ({
                ...prevData,
                phoneNumber: `${input.slice(0, 3)}-${input.slice(3)}`
            }));
        } else {
            setFormData((prevData) => ({
                ...prevData,
                phoneNumber: `${input.slice(0, 3)}-${input.slice(3, 7)}-${input.slice(7, 11)}`
            }));
        }
    };

    const validatePhoneNumber = (phoneNumber) => {
        // 한국 휴대전화 형식(010-XXXX-XXXX) 검증
        const phoneRegex = /^010-\d{4}-\d{4}$/;
        return phoneRegex.test(phoneNumber);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handlePasswordChange = (e) => {
        const { name, value } = e.target;
        setPasswordData((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleProfileSubmit = async (e) => {
        e.preventDefault();
        // 전화번호 검증 로직 추가
        if (!validatePhoneNumber(formData.phoneNumber)) {
            setPhoneError('올바른 전화번호 형식이 아닙니다. (010-XXXX-XXXX)');
            return;
        } else {
            setPhoneError('');
        }

        try {
            const requestData = {
                name: formData.name,
                email: formData.email,
                gender: formData.gender,
                phoneNumber: formData.phoneNumber.replace(/-/g, ''), // 전화번호에서 '-' 제거
                birthdate: formData.birthdate,
            };

            await api.put('/users/me', requestData);
            alert('Profile updated successfully');
        } catch (error) {
            console.error('Failed to update profile', error);
            alert('Failed to update profile: ' + (error.response?.data?.result?.resultMessage || 'Unknown error'));
        }
    };

    const handlePasswordSubmit = async (e) => {
        e.preventDefault();
        if (passwordData.newPassword !== passwordData.confirmPassword) {
            alert('New password and confirmation do not match');
            return;
        }
        try {
            const requestData = {
                currentPassword: passwordData.currentPassword,
                newPassword: passwordData.newPassword
            };

            await api.put('/users/me/password', requestData);
            alert('Password updated successfully');
        } catch (error) {
            console.error('Failed to update password', error);
            alert('Failed to update password: ' + (error.response?.data?.result?.resultMessage || 'Unknown error'));
        }
    };

    return (
        <div className="edit-profile-container">
            <h2>Edit Profile</h2>
            {user && (
                <>
                    <form onSubmit={handleProfileSubmit} className="edit-profile-form">
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
                                onChange={handlePhoneNumberChange}
                                placeholder="010-1234-5678"
                                required
                            />
                            {phoneError && <p className="error">{phoneError}</p>}
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
                        <button type="submit">Update Profile</button>
                    </form>

                    {!isGoogleUser && (
                        <>
                            <h2>Change Password</h2>
                            <form onSubmit={handlePasswordSubmit} className="edit-password-form">
                                <div className="form-group">
                                    <label>Current Password</label>
                                    <input
                                        type="password"
                                        name="currentPassword"
                                        value={passwordData.currentPassword}
                                        onChange={handlePasswordChange}
                                        required
                                    />
                                </div>
                                <div className="form-group">
                                    <label>New Password</label>
                                    <input
                                        type="password"
                                        name="newPassword"
                                        value={passwordData.newPassword}
                                        onChange={handlePasswordChange}
                                        required
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Confirm New Password</label>
                                    <input
                                        type="password"
                                        name="confirmPassword"
                                        value={passwordData.confirmPassword}
                                        onChange={handlePasswordChange}
                                        required
                                    />
                                </div>
                                <button type="submit">Change Password</button>
                            </form>
                        </>
                    )}
                </>
            )}
        </div>
    );
};

export default EditProfile;
