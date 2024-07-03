import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

export const PrivateRoute = ({ children, isAuthenticated }) => {
    const location = useLocation();

    if (!isAuthenticated) {
        return <Navigate to="/login" state={{ from: location }} />;
        alert(location);
    }

    return children;
};

export const AdminRoute = ({ children, isAuthenticated, isAdmin }) => {
    const location = useLocation();

    console.log('AdminRoute - isAuthenticated:', isAuthenticated);
    console.log('AdminRoute - isAdmin:', isAdmin);

    if (!isAuthenticated) {
        return <Navigate to="/login" state={{ from: location }} />;
    }

    if (!isAdmin) {
        return <Navigate to="/news" />;
    }

    return children;
};