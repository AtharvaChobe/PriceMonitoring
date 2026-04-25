import { useEffect, useState } from "react";
import { createAlert, getAlerts, deleteAlert, getCurrentPrice } from "../api/api";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

const cities = ["Mumbai", "Delhi", "Chennai", "Hyderabad", "Banglore"];
const metals = ["Gold", "Silver"];

export default function Home() {
    const [alerts, setAlerts] = useState([]);
    const [form, setForm] = useState({
        city: "Mumbai",
        metal: "Gold",
        targetPrice: "",
    });
    const [currentPrice, setCurrentPrice] = useState(null);

    const navigate = useNavigate();
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token) navigate("/");
    }, []);

    let user;
    try {
        user = jwtDecode(token);
    } catch {
        localStorage.removeItem("token");
        window.location.href = "/";
    }

    const loadAlerts = async () => {
        try {
            const res = await getAlerts(user.userId);
            setAlerts(res.data);
        } catch (e) {
            toast.error("Failed to load alerts");
        }
    };

    useEffect(() => {
        loadAlerts();
    }, []);

    useEffect(() => {
        fetchPrice(form.metal, form.city);
    }, []);

    const handleCreate = async () => {
        if (!form.targetPrice) {
            toast.error("Enter target price");
            return;
        }

        try {
            await createAlert({
                ...form,
                userId: user.userId,
            });

            toast.success("Alert created successfully");
            setForm({ ...form, targetPrice: "" });
            loadAlerts();
        } catch {
            toast.error("Failed to create alert");
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteAlert(id);
            toast.success("Alert deleted");
            loadAlerts();
        } catch {
            toast.error("Delete failed");
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        toast.success("Logged out");
        navigate("/");
    };

    const fetchPrice = async (metal, city) => {
        try {
            const res = await getCurrentPrice(metal, city);
            setCurrentPrice(res.data);
            toast.success(`Price updated: ₹${res.data}`);
        } catch (e) {
            toast.error("Failed to fetch current price");
        }
    };

    return (
        <div className="p-6 bg-gray-50 min-h-screen">
            <div className="flex justify-between mb-6">
                <h1 className="text-2xl font-bold">Price Alerts</h1>
                <button
                    onClick={logout}
                    className="bg-red-500 cursor-pointer text-white px-4 py-1 rounded"
                >
                    Logout
                </button>
            </div>

            {/* Create Alert */}
            <div className="bg-white p-4 rounded shadow mb-6">
                <div className="flex gap-3 flex-wrap">
                    <select
                        className="p-2 border"
                        value={form.city}
                        onChange={(e) => {
                            const city = e.target.value;
                            setForm({ ...form, city });
                            fetchPrice(form.metal, city);
                        }}
                    >
                        {cities.map((c) => (
                            <option key={c}>{c}</option>
                        ))}
                    </select>

                    <select
                        className="p-2 border"
                        value={form.metal}
                        onChange={(e) => {
                            const metal = e.target.value;
                            setForm({ ...form, metal });
                            fetchPrice(metal, form.city);
                        }}
                    >
                        {metals.map((m) => (
                            <option key={m}>{m}</option>
                        ))}
                    </select>

                    <input
                        className="p-2 border"
                        placeholder="Target Price"
                        value={form.targetPrice}
                        onChange={(e) =>
                            setForm({ ...form, targetPrice: e.target.value })
                        }
                    />

                    <div className="mt-3">
                        <p className="text-gray-600">
                            Current Price:{" "}
                            {currentPrice ? (
                                <span className="font-semibold text-green-600">
                                    ₹ {currentPrice}
                                </span>
                            ) : (
                                "Loading..."
                            )}
                        </p>
                    </div>

                    <button
                        onClick={handleCreate}
                        className="bg-blue-500 text-white px-4 py-2 rounded"
                    >
                        Add Alert
                    </button>
                </div>
            </div>

            {/* Alerts List */}
            <div className="space-y-3">
                {alerts.map((a) => (
                    <div
                        key={a.id}
                        className="flex justify-between bg-white p-4 rounded shadow"
                    >
                        <div>
                            <p className="font-semibold">
                                {a.metal} - {a.city}
                            </p>
                            <p className="text-gray-600">₹ {a.targetPrice}</p>
                        </div>

                        <button
                            onClick={() => handleDelete(a.id)}
                            className="text-red-500 cursor-pointer"
                        >
                            Delete
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}