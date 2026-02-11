import backgroundImage from '../public/bg-game.png';
import banner from "../src/assets/welcomebanner.svg";
import buttonImg from "../src/assets/str-button.svg";

function App() {
    return (
        <div
            className="min-h-screen w-full bg-cover bg-center bg-no-repeat flex flex-col items-center justify-center p-4"
            style={{ backgroundImage: `url(${backgroundImage})` }}
        >
            {/* responsive window  each banner size depend on the User display s md lg and to make it smoother we call transition-all*/}
            <div className="w-full max-w-[300px] md:max-w-[500px] lg:max-w-[700px] mb-8 transition-all">
                <img
                    src={banner}
                    alt="Game Banner"
                    className="w-full h-auto drop-shadow-2xl"
                />
            </div>


            <button
                className="relative w-full max-w-[200px] md:max-w-[250px] hover:scale-110 transition-transform duration-200 active:brightness-90 active:scale-95"
            >
                <img
                    src={buttonImg}
                    alt="Start Button"
                    className="w-full h-auto"
                />
                {/* ถ้าในไฟล์ SVG ไม่มีคำว่า Start Game ให้ใช้ Span นี้แสดงแทน */}
                <span className="absolute inset-0 flex items-center justify-center text-white font-black text-lg md:text-2xl drop-shadow-md select-none">
                </span>
            </button>
        </div>
    );
};

export default App;