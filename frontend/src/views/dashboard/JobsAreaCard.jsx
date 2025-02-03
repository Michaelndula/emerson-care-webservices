 import React from 'react';
import { useSelector } from 'react-redux';

// material-ui
import { useTheme } from '@mui/material/styles';
import { Card, Grid, Typography, CardMedia } from '@mui/material';

// third-party
import ApexCharts from 'apexcharts';
import Chart from 'react-apexcharts';

// project imports
import chartData from './chart-data/bajaj-area-chart';
import Jobs from 'assets/images/jobs.jpg';

// ===========================|| DASHBOARD DEFAULT - BAJAJ AREA CHART CARD ||=========================== //

const JobsAreaCard = () => {
  const theme = useTheme();
  const orangeDark = theme.palette.secondary[800];

  const customization = useSelector((state) => state.customization);
  const { navType } = customization;

  React.useEffect(() => {
    const newSupportChart = {
      ...chartData.options,
      colors: [orangeDark],
      tooltip: { theme: 'light' }
    };
    ApexCharts.exec(`support-chart`, 'updateOptions', newSupportChart);
  }, [navType, orangeDark]);

  return (
      <Card sx={{ bgcolor: 'secondary.light' }}>

        {/* Display Image Instead of Chart */}
        <CardMedia
          component="img"
          image={Jobs}
          alt="Jobs Image"
          sx={{ height: 150, objectFit: 'cover' }}
        />
      </Card>
    );
  };


export default JobsAreaCard;
