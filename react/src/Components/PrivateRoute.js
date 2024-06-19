// src/components/PrivateRoute.js
import React from 'react';
import { Navigate } from 'react-router-dom';

export const PrivateRoute = ({ children, isAuthenticated }) => {
    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    return children;
};

export const AdminRoute = ({ children, isAuthenticated, isAdmin }) => {
    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    if (!isAdmin) {
        return <Navigate to="/news" />;
    }

    return children;
};
