import { useEffect, useState } from 'react';

// material-ui
import Grid from '@mui/material/Grid';

// project imports
import  UsersCard from './UsersCard';
import PopularCard from './PopularCard';
import TotalPatientsCard from './TotalPatientsCard';
import TotalIncomeDarkCard from './TotalIncomeDarkCard';
import TotalProvidersCard from './TotalProvidersCard';
import TotalGrowthBarChart from './TotalGrowthBarChart';
import JobsCard from './JobsCard';

import { gridSpacing } from 'store/constant';

// assets
import StorefrontTwoToneIcon from '@mui/icons-material/StorefrontTwoTone';

// ==============================|| DEFAULT DASHBOARD ||============================== //

const Dashboard = () => {
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, []);

  return (
    <Grid container spacing={gridSpacing}>
      <Grid item xs={12}>
        <Grid container spacing={gridSpacing}>
          <Grid item xs={3}>
                <UsersCard isLoading={isLoading} />
              </Grid>
              <Grid item xs={3}>
                <TotalPatientsCard isLoading={isLoading} />
              </Grid>
              <Grid item xs={3}>
                <TotalProvidersCard isLoading={isLoading} />
              </Grid>
              <Grid item xs={3}>
                <JobsCard isLoading={isLoading} />
              </Grid>
        </Grid>
      </Grid>
      <Grid item xs={12}>
        <Grid container spacing={gridSpacing}>
          <Grid item xs={12} md={8}>
            <TotalGrowthBarChart isLoading={isLoading} />
          </Grid>
          <Grid item xs={12} md={4}>
            <PopularCard isLoading={isLoading} />
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Dashboard;
