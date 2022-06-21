import React, { useState, useEffect } from 'react';
import Header from '../../components/Header';
import TableDashboard from '../../components/TableDashboard';
import Footer from '../../components/Footer';
import './Dashboard.css';

export default function Dashboard() {
    return(
        <div>
            <Header/>
            <div className='body-dashboard'>
                

                <div className='body-dashboard-button'>
                    <button>Adicionar </button>
                </div>

                <div className='body-dashboard-table'>
                    <TableDashboard/>
                </div>

            </div>
            <Footer/>
        </div>
    )
}