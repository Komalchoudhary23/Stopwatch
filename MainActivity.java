{

    private TextView tvTimer;
    private Button btnStart, btnStop, btnHold;
    private Handler handler;
    private long startTime, timeInMillis;
    private boolean isRunning = false;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnHold = findViewById(R.id.btnHold);

        handler = new Handler();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis();
                    handler.postDelayed(runnable, 0);
                    isRunning = true;
                    isPaused = false;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                    isPaused = false;
                    tvTimer.setText("00:00:00.000");
                }
            }
        });

        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning && !isPaused) {
                    handler.removeCallbacks(runnable);
                    isPaused = true;
                } else if (isPaused) {
                    startTime = System.currentTimeMillis() - timeInMillis;
                    handler.postDelayed(runnable, 0);
                    isPaused = false;
                }
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            int seconds = (int) (timeInMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (timeInMillis % 1000);

            tvTimer.setText(String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds));

            handler.postDelayed(this, 10);
        }
    };
}
