import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Typography,
  Box,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Chip,
  Button,
  Modal,
  TextField,
  IconButton
} from "@mui/material";
import DashboardCard from "ui-component/cards/DashboardCard";
import CloseIcon from "@mui/icons-material/Close";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import useJobs from "../../hooks/useJobs";

const truncateText = (text, length = 10) => {
  return text.length > length ? `${text.substring(0, length)}...` : text;
};

const JobsAll = () => {
  const { jobs, loading, updateJob, deleteJob } = useJobs();
  const [openModal, setOpenModal] = useState(false);
  const [selectedJob, setSelectedJob] = useState(null);
  const [deleteConfirmModal, setDeleteConfirmModal] = useState(false);
  const [jobToDelete, setJobToDelete] = useState(null);
  const [statusModal, setStatusModal] = useState({ open: false, message: "", isSuccess: false });
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    deadline: "",
    posterImage: null,
  });

  const handleOpenModal = (job) => {
    setSelectedJob(job);
    setFormData({
      title: job.title,
      description: job.description,
      deadline: job.deadline,
      posterImage: null,
    });
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setSelectedJob(null);
  };

  const handleChange = (e) => {
    if (e.target.name === "posterImage") {
      setFormData({ ...formData, posterImage: e.target.files[0] });
    } else {
      setFormData({ ...formData, [e.target.name]: e.target.value });
    }
  };

  const handleUpdateJob = async () => {
    if (!selectedJob) return;

    const data = new FormData();
    data.append("title", formData.title);
    data.append("description", formData.description);
    data.append("deadline", formData.deadline);
    if (formData.posterImage) {
      data.append("posterImage", formData.posterImage);
    }

    try {
      await updateJob(selectedJob.id, data);
      setStatusModal({ open: true, message: "Job updated successfully!", isSuccess: true });
      handleCloseModal();
    } catch (error) {
      console.error("Error updating job:", error);
      setStatusModal({ open: true, message: "Job update failed!", isSuccess: false });
    }
  };

  const handleDeleteJob = async () => {
    if (!jobToDelete) return;

    try {
      await deleteJob(jobToDelete.id);
      setStatusModal({ open: true, message: "Job deleted successfully!", isSuccess: true });
      setDeleteConfirmModal(false);
      setJobToDelete(null);
    } catch (error) {
      console.error("Error deleting job:", error);
      setStatusModal({ open: true, message: "Job deletion failed!", isSuccess: false });
    }
  };

  const openDeleteConfirmModal = (job) => {
    setJobToDelete(job);
    setDeleteConfirmModal(true);
  };

  const closeDeleteConfirmModal = () => {
    setDeleteConfirmModal(false);
    setJobToDelete(null);
  };

  useEffect(() => {
    if (statusModal.open) {
      const timer = setTimeout(() => {
        setStatusModal((prev) => ({ ...prev, open: false }));
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [statusModal]);

  return (
    <DashboardCard title="All Jobs">
      <Box sx={{ overflow: "auto", width: { xs: "280px", sm: "auto" } }}>
        {loading ? (
          <Typography variant="subtitle1">Loading...</Typography>
        ) : jobs.length === 0 ? (
          <Typography variant="subtitle1">No Jobs Posted Yet</Typography>
        ) : (
          <Table aria-label="jobs table" sx={{ whiteSpace: "nowrap", mt: 2 }}>
            <TableHead>
              <TableRow>
                <TableCell>
                  <Typography fontWeight={600}>Title</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Description</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Type</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Location</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Rate</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Posted Date</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Deadline</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Applications</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Status</Typography>
                </TableCell>
                <TableCell>
                  <Typography fontWeight={600}>Action</Typography>
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {jobs.map((job) => (
                <TableRow key={job.id}>
                  <TableCell>
                    <Typography fontWeight={500}>{job.title}</Typography>
                  </TableCell>
                  <TableCell>
                    <Typography variant="body2">{truncateText(job.description)}</Typography>
                  </TableCell>
                  <TableCell>{job.type}</TableCell>
                  <TableCell>{job.location}</TableCell>
                  <TableCell>{job.rate}</TableCell>
                  <TableCell>{job.postedDate}</TableCell>
                  <TableCell>{job.deadline}</TableCell>
                  <TableCell>{job.applicationCount}</TableCell>
                  <TableCell>
                    <Chip label="Active" sx={{ backgroundColor: "success.main", color: "#fff" }} />
                  </TableCell>
                  <TableCell>
                    <IconButton onClick={() => handleOpenModal(job)} color="primary">
                      <EditIcon />
                    </IconButton>
                    <IconButton onClick={() => openDeleteConfirmModal(job)} color="error">
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </Box>

      {/* Update Job Modal */}
      <Modal open={openModal} onClose={handleCloseModal}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
            borderRadius: "8px",
          }}
        >
          <Box display="flex" justifyContent="space-between" alignItems="center">
            <Typography variant="h6">Update Job</Typography>
            <IconButton onClick={handleCloseModal}>
              <CloseIcon />
            </IconButton>
          </Box>
          <TextField
            label="Title"
            fullWidth
            margin="normal"
            name="title"
            value={formData.title}
            onChange={handleChange}
          />
          <TextField
            label="Description"
            fullWidth
            margin="normal"
            name="description"
            value={formData.description}
            onChange={handleChange}
            multiline
            rows={3}
          />
          <TextField
            label="Deadline"
            fullWidth
            margin="normal"
            name="deadline"
            type="date"
            value={formData.deadline}
            onChange={handleChange}
          />
          <input
            type="file"
            name="posterImage"
            accept="image/*"
            onChange={handleChange}
            style={{ marginTop: "10px" }}
          />
          <Button
            variant="contained"
            color="primary"
            fullWidth
            onClick={handleUpdateJob}
            sx={{ mt: 2 }}
          >
            Save Changes
          </Button>
        </Box>
      </Modal>

      {/* Status Modal */}
      <Modal open={statusModal.open} onClose={() => setStatusModal((prev) => ({ ...prev, open: false }))}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 300,
            bgcolor: "background.paper",
            p: 4,
            textAlign: "center",
            borderRadius: "8px",
          }}
        >
          <Typography variant="h6" color={statusModal.isSuccess ? "green" : "red"}>
            {statusModal.message}
          </Typography>
        </Box>
      </Modal>

      {/* Delete Confirmation Modal */}
      <Modal open={deleteConfirmModal} onClose={closeDeleteConfirmModal}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 300,
            bgcolor: "background.paper",
            p: 4,
            textAlign: "center",
            borderRadius: "8px",
          }}
        >
          <Typography variant="h6">Are you sure you want to delete?</Typography>
          <Button onClick={handleDeleteJob} color="error" variant="contained" sx={{ mt: 2 }}>
            Delete
          </Button>
        </Box>
      </Modal>
    </DashboardCard>
  );
};

export default JobsAll;