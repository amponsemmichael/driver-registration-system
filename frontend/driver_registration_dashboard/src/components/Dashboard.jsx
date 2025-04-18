import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const Dashboard = () => {
  const { id } = useParams();
  const [driver, setDriver] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDriverData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/drivers/${id}`);
        setDriver(response.data);
      } catch (err) {
        console.error('Error fetching driver data:', err);
        setError('Failed to load driver information');
      } finally {
        setLoading(false);
      }
    };

    fetchDriverData();
  }, [id]);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error-container">{error}</div>;
  }

  if (!driver) {
    return <div className="not-found">Driver not found</div>;
  }

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h2>Driver Dashboard</h2>
        <div className="success-message">
          Registration Successful!
        </div>
      </div>

      <div className="driver-info-card">
        <h3>Driver Information</h3>
        
        <div className="info-row">
          <div className="info-label">Name:</div>
          <div className="info-value">{driver.fullName}</div>
        </div>
        
        <div className="info-row">
          <div className="info-label">Email:</div>
          <div className="info-value">{driver.email}</div>
        </div>
        
        <div className="info-row">
          <div className="info-label">Phone:</div>
          <div className="info-value">{driver.phone}</div>
        </div>
        
        <div className="info-row">
          <div className="info-label">Truck Type:</div>
          <div className="info-value">{driver.truckType}</div>
        </div>
        
        <div className="info-row">
          <div className="info-label">Document:</div>
          <div className="info-value document-link">
            <a 
              href={driver.documentDownloadUrl} 
              target="_blank" 
              rel="noopener noreferrer"
            >
              {driver.documentName} (View/Download)
            </a>
          </div>
        </div>
      </div>

      <div className="dashboard-actions">
        <Link to="/" className="button secondary-button">
          Back to Registration
        </Link>
      </div>
    </div>
  );
};

export default Dashboard;